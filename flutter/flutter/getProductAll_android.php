<?php

require('config.php');

$selectQuery = "select * from tblproduct ORDER BY id DESC";
$result = $db_con->query($selectQuery);
$rowCount = $result->num_rows;

if($rowCount>0){
	$getData = array();
	while($rows = mysqli_fetch_assoc($result)){
		$dataArray['id'] = $rows['id'];
		$dataArray['categoryId'] = $rows['categoryId'];
		$dataArray['name'] = $rows['name'];
		$dataArray['price'] = $rows['price'];
		$dataArray['unit'] = $rows['unit'];
		$dataArray['description'] = $rows['description'];
		$dataArray['image'] = $product_path.$rows['image'];
		array_push($getData, $dataArray);
	}
	$data = array("Status"=>true,"Message"=>"Product List","ProductData"=>$getData);
	print(json_encode($data));
}
else{
	$data = array("Status"=>false,"Message"=>"Product Not Found");
	print(json_encode($data));
}

?>