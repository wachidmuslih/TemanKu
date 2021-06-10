<?php

$server = "localhost";
$user = "root";
$namadb = "tiumy";
$password = "";

$conn = mysqli_connect ($server, $user, $password, $namadb) or die ("Koneksi gagal");

$nama = $_POST['nama'];
$telpon = $POST['telpon'];
class emp{}
$query = mysqli_query ( $conn, "INSERT INTO teman (nama, telpon) VALUES ('Snama' , 'Stelpon')");

if ($query){
    $response = new emp();
    $response -> success = 1;
    $response -> message = "Data berhasil disimpan";
    die (json_encode ($response));
}

else{
    $response = new emp ();
    $response -> success = 0;
    $response -> message = "Gagal menyimpan data";
    die  (json_encode($response));
}
?>