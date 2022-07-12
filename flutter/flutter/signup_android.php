<?php

require('config.php');
error_reporting(0);

$name = $_POST['name'];
$email = $_POST['email'];
$contact = $_POST['contact'];
$password = $_POST['password'];
$city = $_POST['city'];
$dob = $_POST['dob'];
$gender = $_POST['gender'];

//echo $name.'_'.$email.'_'.$contact;

$selectQuery = "select * from users WHERE email='".$email."' OR contact='".$contact."'";

$selectResult = $db_con->query($selectQuery);
$rowCount = $selectResult->num_rows;

if($rowCount>0){
		//echo "Unsuccess";
	$data = array("Status"=>false,"Message"=>"EmailId/Contact No. Already Registered");
		print(json_encode($data));
}
else{
	$insertQuery = "insert into users (`name`,`email`,`contact`,`password`,`gender`,`city`,`dob`) VALUES ('".$name."','".$email."','".$contact."','".$password."','".$gender."','".$city."','".$dob."')";

	$result = $db_con->query($insertQuery);

	if($result){
		//echo "Success";
		$data = array("Status"=>true,"Message"=>"Signup Successfully");
		print(json_encode($data));
	}
	else{
		//echo "Unsuccess";
	$data = array("Status"=>false,"Message"=>"Signup Unsuccessfully");
		print(json_encode($data));
	}	
}


?>