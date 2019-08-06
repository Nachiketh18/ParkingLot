import { Component,OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  // call get method on initialize
  ngOnInit(){
    this.getAllParking();
  }

  //as both url are same
  baseUrl="http://localhost:8080/api/parkings";

  error_message="";
  errorAlert=false;

  //below fields has been renamed in html accordingly
  parkingLots={

    "created_at": [null],
    "id": [],
    "lot": ['',Validators.required],
    "parking_duration": [60,Validators.required],
    "parking_amount": [20,Validators.required],
    "updatedAt": [null],
    "vehicle_number": ['',Validators.required]

  };

  parkings:any=[];

  constructor(private formBuilder: FormBuilder,private http:HttpClient) { }

  vehicleForm: FormGroup = this.formBuilder.group(this.parkingLots)



  calculateAmount(event){
    let duration=event.target.value;

    if(duration<60){
      this.vehicleForm.get("parking_amount").setValue(20);
    }
    this.vehicleForm.get("parking_amount").setValue(Math.round(0.333*duration));

  }

  onSubmit(){
    console.log(this.vehicleForm.value)
    if(this.vehicleForm.valid){
      this.parkNewvehicle(this.vehicleForm.value);
    }
  }

  // Serivce Call

  getAllParking(){
    this.http.get(this.baseUrl).subscribe(body=>{
      this.parkings=body;
      this.vehicleForm.get("lot").setValue(this.parkings.length);
    })
  }

  parkNewvehicle(vehicle){
    this.http.post(this.baseUrl,vehicle).subscribe((response)=>{
      this.vehicleForm.reset();
	this.getAllParking();
    },err=>{
      err=err.error
      console.log(err);
      this.errorAlert=true;
      this.error_message=err.message;
    })
  }





}
