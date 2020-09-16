<?php

$con=mysqli_connect("localhost","root","","nobell");

if(mysqli_connect_errno($con)){
	
	echo "Failed to connect to MySQL : ".mysqli_connect_error();
}

mysqli_set_charset($con, "utf8");

$res = mysqli_query($con, "SELECT * FROM restaurant");

$result = array();

while($row = mysqli_fetch_array($res)){
	
	
	array_push($result,
		array('rs_id'=>$row[0],'rs_name'=>$row[1],'rs_phone'=>$row[2],'rs_address'=>$row[3],'rs_intro'=>$row[4],'rs_open'=>$row[5],'rs_close'=>$row[6],'rs_owner'=>$row[7]));
}

echo json_encode(array("result"=>$result));

mysqli_close($con);

?>