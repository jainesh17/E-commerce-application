<?php

require('config.php');
error_reporting(0);

$email = $_POST['email'];
$password = $_POST['password'];

$loginquery = "select * from users WHERE (email='".$email."' OR contact='".$email."') AND password='".$password."'";

$result = $db_con->query($loginquery);

$rowCount =$result->num_rows;

if($rowCount>0){

	while($rows = mysqli_fetch_assoc($result)){
		$dataArray[] = $rows;
	}
	$data = array('Status'=>true,'Message'=>"Login Successfully",'UserData'=>$dataArray);

	/*$getData = array();

	while($rows = mysqli_fetch_assoc($result)){
		$dataArray['userId'] = $rows['id'];
		$dataArray['name'] = $rows['name'];
		$dataArray['email'] = $rows['email'];
		$dataArray['contact'] = $rows['contact'];
		$dataArray['gender'] = $rows['gender'];
		$dataArray['city'] = $rows['city'];
		array_push($getData, $dataArray);
	}
	$data = array('Status'=>true,'Message'=>"Login Successfully",'UserData'=>$getData);*/
	print(json_encode($data));
}
else{
	$data = array('Status'=>false,'Message'=>"Login Unsuccessfully");
	print(json_encode($data));
}

?>