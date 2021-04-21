package com.example.School.Controller;

import brave.sampler.Sampler;
import com.example.School.Model.Employees;
import com.example.School.Model.PersonalDetails;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/schooldetails")
public class SchoolDetails {

    @Autowired
    private RestTemplate restTemplate;
    /*RestTemplate restTemplate=new RestTemplate();*/

    @Autowired
    private WebClient.Builder getWebClientBuilder;

    public Sampler defaultSampler(){
        return Sampler.ALWAYS_SAMPLE;
    }

    /*webclient for reactive/assynchronous(not stepbystep procedure) programming  ; lamda programming used webclient*/

                    /*<------------------ Employee API's -------------->*/

    @GetMapping("/allEmployees")
    public Employees[] getEmployeesDetails(){

        System.out.println("<---------Inside All Employee Method----------->");
        ResponseEntity<Employees[]> response =  restTemplate.getForEntity(
                "http://school-employee-service/employee/getAllEmp",Employees[].class);
        Employees[] employees = response.getBody();
        System.out.println("<---After Execution--->");
       return  employees;

    }



    /*FallBack Machanism Start*/
    /*fallBack mechanism only applicable when calling external class/api calling ex. employee,student.
    * fallBack won't work(throws an error) if the same class/api where method available itself ex.school */

    /*<----FallBack method Example(Fallback machanism)-->*/
    @GetMapping("/employeeById/{id}")
    @HystrixCommand(fallbackMethod = "getFallbackSchool"
     /*<----commandProperties is an array of Hystrix command--->  , commandProperties = {
    @HystrixProperty(name="execution.isolation.thread.timeoutInMillisecods",value="2000"
    }*/)
   public ResponseEntity<Employees> getEmpById(@PathVariable(value="id") Long empId ){
     //   RestTemplate restTemplate=new RestTemplate();
       final String url="http://school-employee-service/employee/getEmp/";
        System.out.println("<---------Inside All Employee By Id Method----------->");
       ResponseEntity<Employees>  emp = restTemplate.getForEntity(url+empId,Employees.class);
        System.out.println("<---Inside EmployeeById Method--->");
       return emp;
    }
    /*<---Same signature of above class for fallbackmethod---- Constant values-->*/
    public ResponseEntity<Employees> getFallbackSchool(@PathVariable(value="id") Long empId ){
        Employees emp=new Employees();
        emp.setId(0);
        emp.setFirstName("No Name");
        emp.setLastName("No Name");
        emp.setEmailId("No Mail");
        System.out.println("<---Inside FallBack Mechanism--->");
        return ResponseEntity.ok().body(emp);
    }
/*FallBack Machanism Ends*/

    @DeleteMapping("/DeleteEmpById/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long empId){
        final String url="http://school-employee-service/employee/deleteEmp/";
        restTemplate.delete(url+empId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }


    /*<------------------ Student API's ------------->*/
   @GetMapping("/personalDetails")
    public PersonalDetails[] getAllPD(){
              PersonalDetails[] pd= getWebClientBuilder.build()
                      .get()
                      .uri("http://school-student-service/student/pd/")
                      .retrieve()
                      .bodyToMono(PersonalDetails[].class)
                      .block();
              return pd;

    }


    @GetMapping("/personalDetails/{id}")
    public ResponseEntity<PersonalDetails> getPDById(@PathVariable(value="id") Integer pDId){
        System.out.println("<---Entering to Execute URL ---->");
        ResponseEntity<PersonalDetails> pd=restTemplate.getForEntity("http://school-student-service/student/pd/"+pDId,PersonalDetails.class);
        System.out.println("<----- After Executing URL -->");
        return pd;
    }

}
