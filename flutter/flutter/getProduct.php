<?php

require('config.php');

$categoryId = $_REQUEST['categoryId'];

$selectQuery = "select * from tblproduct WHERE categoryId='".$categoryId."' ORDER BY id DESC";
$result = $db_con->query($selectQuery);
$rowCount = $result->num_rows;

if($rowCount>0){
	$getData = array();
	while($rows = mysqli_fetch_assoc($result)){
		$dataArray['id'] = $rows['id'];
		$dataArray['categoryId'] = $rows['categoryId'];
		$dataArray['name'] = $rows['name'];
		$dataArray['price'] = $rows['price'];
		$dataArray['description'] = $rows['description'];
		$dataArray['image'] = $product_path.$rows['image'];
		array_push($getData, $dataArray);
	}
	print(json_encode($getData));
}
else{
	$data = array("Status"=>false,"Message"=>"Product Not Found");
	print(json_encode($data));
}

?>