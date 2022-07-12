<?php

require('config.php');

$selectQuery = "select * from tblcategory ORDER BY id DESC";
$result = $db_con->query($selectQuery);
$rowCount = $result->num_rows;

if($rowCount>0){
	$getData = array();
	while($rows = mysqli_fetch_assoc($result)){
		$dataArray['id'] = $rows['id'];
		$dataArray['name'] = $rows['name'];
		$dataArray['image'] = $category_path.$rows['image'];
		array_push($getData, $dataArray);
	}
	$data = array("Status"=>true,"Message"=>"Category Lists","CategoryData"=>$getData);
	print(json_encode($data));
}
else{
	$data = array("Status"=>false,"Message"=>"Category Not Found");
	print(json_encode($data));
}

?>