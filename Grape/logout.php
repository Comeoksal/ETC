<?php
ob_start();
// Redirect to the login page
header("Location: index.php");
exit();
ob_end_flush();
?>
