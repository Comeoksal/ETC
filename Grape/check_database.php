<?php

    $db = new SQLite3('./SQLite_DB/cat_homepage.db');

    $enteredUsername = "grape";
    $enteredPassword = "secret1234";

    $query = "SELECT * FROM users WHERE username='" . $enteredUsername . "' AND password='" . $enteredPassword . "'";
   
		$result = $db->query($query);
	  $row = $result->fetchArray();
   

    echo "your query : $query";
    echo "<br>";
    
   

    echo "row : $row <br>";
    echo "<br>";
    
    echo "row['username'] : {$row['username']} <br>";
    echo "row['password'] : {$row['password']} <br>";

    echo "<br>";

    if ($row) {
        echo "this is on database : $enteredUsername/$enteredPassword";
        echo $result->fetchArray();
    }else{
        echo "not in database : $enteredUsername/$enteredPassword";
    }

?>