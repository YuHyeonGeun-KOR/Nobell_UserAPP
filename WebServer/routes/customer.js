var express = require('express');
var mysql = require('mysql');

var mysql = require('mysql');
var pool = mysql.createPool({
  connectionLimit: 10,
  host: 'localhost',
  user: 'root',
  database: 'nobell',
  password : 'asdasd'
 
});

var router = express.Router();

/* GET users listing. */
router.post('/', function(req, res, next) {
    res.send(200);
});
router.post('/check_connection', function(req, res) {
    res.send("connected successfully");
});

router.post('/test', function(req, res) {
    res.send("LogOK");
});
// Register
router.post('/register', function(req, res, next) {

  var customer_name = req.body.reg_name;
  var customer_email = req.body.reg_email;
  var customer_pwd = req.body.reg_pwd;
  var customer_phone = req.body.reg_phone;
  var message = 'Exist';

  var check_data = null;
 
  var reg_data = [customer_email, customer_name,customer_pwd, customer_phone];

  console.log('-> Get Data = ', reg_data);
  pool.getConnection(function(err, connection){
      
    connection.query("INSERT INTO nobell.customer_tbl(customer_email, customer_name, customer_pw, customer_phone) values(?,?,?,?)", reg_data, function(err,data){
        if(err) {
            if(err.errno == 1062)
                res.send("Exist");
            else{
                res.send('Resiter error')
            }
        }
        else {
            res.send("RegOK");
        }
    });
    connection.release();
  });
});


// Login
router.post('/login', function(req, res, next) {

  console.log('-> Get Email = ', req.body.login_email, '-> Get pwd = ' ,req.body.login_pwd);

  var login_email = req.body.login_email;
  var login_pwd = req.body.login_pwd;

  var login_data = [login_email, login_pwd];
  
  pool.getConnection(function(err, connection){

      connection.query("select * FROM nobell.customer_tbl WHERE customer_email=? AND customer_pw=?", login_data, function(err, data){

          if(err) {
              res.send('Error');
          }
          else {
              if(data == "") {
                  res.send('Incorrect');
              }

              else {
                  res.send("LogOK");
              }
          }
      });
      connection.release();
  });
});


//Update
router.post('/update', function(req, res, next) {

    console.log('-> Get Email = ', req.body.update_email, '-> Get pwd = ' ,req.body.update_pwd);
  
    var update_email = req.body.update_email;
    var update_pwd = req.body.update_pwd;
  
    var update_data = [update_pwd,update_email];
    
    pool.getConnection(function(err, connection){
  
        connection.query("update nobell.customer_tbl SET customer_pw = ? WHERE customer_email = ?", update_data, function(err, data){
  
            if(err) {
                res.send('Error');
            }
            else {
                
                    res.send("UptOK");

                
            }
        });
      connection.release();
    });
  });


router.get('/res_View', function(req, res, next) {


    pool.getConnection(function(err, connection){
  
        connection.query("SELECT * FROM nobell.restaurant_tbl" ,function(err, data){
  
          console.log("result", data);
          res.send(data);
        });
        connection.release();
    });
  });
  




router.post('/res_menu', function(req, res, next) {

    console.log('-> Get menuName = ', req.body.rs_name);
  
    var rs_name = req.body.rs_name;
   
  
    var name = [rs_name];
    
    pool.getConnection(function(err, connection){
        connection.query("select * FROM nobell.menu_tbl WHERE menu_rs_id=(select rs_id FROM nobell.restaurant_tbl WHERE rs_name=?)", name, function(err, data){
            console.log("result", data);
            res.send(data);
            
       
        });
        connection.release();
    });
  });
  


  router.post('/res_menu2', function(req, res, next) {

    console.log('-> Get menuName = ', req.body.rs_id);
  
    var rs_id = req.body.rs_id;
   
  
    var name = [rs_id];
    
    pool.getConnection(function(err, connection){
        connection.query("select * FROM nobell.menu_tbl WHERE menu_rs_id= ?", name, function(err, data){
            console.log("result", data);
            res.send(data);
            
       
        });
        connection.release();
    });
  });
  

  router.post('/res_table', function(req, res, next) {

    console.log('-> Get menuName = ', req.body.rs_name);
  
    var rs_name = req.body.rs_name;
   
  
    var table = [rs_name];
    
    pool.getConnection(function(err, connection){
  
        connection.query("select * FROM nobell.table_tbl WHERE table_rs_id=(select rs_id FROM nobell.restaurant_tbl WHERE rs_name=?)", table, function(err, data){
            if(err) console.log(err);
            else console.log("result", data);
            res.send(data);
            
       
        });
        connection.release();
    });
  });
  

  router.post('/res_res', function(req, res, next) {

    console.log('-> Get menuName = ', req.body.rs_name,'-> Get tbl_n = ' ,req.body.tbl_n,'-> Get customer_email = ' ,req.body.customer_email,'-> Get headcount = ' ,req.body.headcount,'-> Get M = ' ,req.body.M,'-> Get D = ' ,req.body.D,'-> Get H = ' ,req.body.H,'-> Get MMI = ' ,req.body.MMi);
  
    var rs_name = req.body.rs_name;
    var tbl_n = req.body.tbl_n;
    var customer_email = req.body.customer_email;
    var headcount = req.body.headcount;
    var M = req.body.M;
    var D = req.body.D;
    var H = req.body.H;
    var MMi = req.body.MMi;
    
    var time = M + "/"+ D + "&" + H + ":" +MMi;
   

  
    var table = [rs_name, tbl_n, customer_email, headcount, time];
    
    
    pool.getConnection(function(err, connection){
  
        connection.query("INSERT INTO nobell.reservation_tbl(rsv_rs_id, rsv_table, rsv_customer, rsv_headcount,rsv_target,rsv_time) values((select rs_id FROM nobell.restaurant_tbl WHERE rs_name=?),?,?,?,?,now())", table, function(err,data){
            if(err) console.log(err);
            else console.log("result", data);
            res.send(data);
            
       
        });
        connection.release();
    });
  });



  router.post('/res_visit', function(req, res, next) {

    console.log('-> Get menuName = ', req.body.rs_name,'-> Get tbl_n = ' ,req.body.tbl_n,'-> Get customer_email = ' ,req.body.customer_email,'-> Get headcount2 = ' ,req.body.headcount2);
  
    var rs_name = req.body.rs_name;
    var headcount2 = req.body.headcount2;
    var customer_email = req.body.customer_email;
    var tbl_n = req.body.tbl_n;
    

  
    var table = [rs_name, tbl_n, customer_email, headcount2];
    var table1 = [customer_email];
    
    pool.getConnection(function(err, connection){
        connection.query("UPDATE nobell.customer_tbl SET customer_state = 1 WHERE customer_email = ?", table1, function(err,data){
            if(err) console.log(err);
            else console.log("result", data);
            
       
        });
        connection.query("INSERT INTO nobell.visit_tbl(visit_rs_id, visit_table, visit_customer, visit_headcount,visit_time) values((select rs_id FROM nobell.restaurant_tbl WHERE rs_name=?),?,?,?,now())", table, function(err,data){
            if(err) console.log(err);
            else console.log("result", data);
            res.send(data);
            
       
        });
        
        connection.release();
    });
  });
 
  router.post('/customer_state', function(req, res, next) {

    console.log('-> Get customer_email = ' ,req.body.customer_email);
  
   
    var customer_email = req.body.customer_email;

  
    var table = [customer_email];
    
    
    pool.getConnection(function(err, connection){
  
        connection.query("Select customer_state FROM nobell.customer_tbl WHERE customer_email = ?", table, function(err,data){
            if(err) console.log(err);
            else console.log("result", data);
            res.send(data);
            
       
        });
        connection.release();
    });
  });
 

  router.post('/customer_state1', function(req, res, next) {

    console.log('-> Get customer_email = ' ,req.body.customer_email);
  
   
    var customer_email = req.body.customer_email;

  
    var table = [customer_email];
    
    
    pool.getConnection(function(err, connection){
  
        connection.query("Select rs_name FROM nobell.restaurant_tbl WHERE rs_id = (Select visit_rs_id FROM nobell.visit_tbl WHERE visit_customer = ?)", table, function(err,data){
            if(err) console.log(err);
            else console.log("result", data);
            res.send(data);
            
       
        });
        connection.release();
    });
  });

  
  router.post('/customer_state2', function(req, res, next) {

    console.log('-> Get customer_email = ' ,req.body.customer_email);
  
   
    var customer_email = req.body.customer_email;
    var rs_id = req.body.rs_id;

  
    var table = [customer_email];
    
    
    pool.getConnection(function(err, connection){
  
        connection.query("Select  table_rs_id , table_no FROM nobell.table_tbl WHERE table_customer = ?", table, function(err,data){
            if(err) console.log(err);
            else console.log("result", data);
            res.send(data);
            
       
        });
        connection.release();
    });
  });

  
  
  router.post('/order', function(req, res, next) {

    console.log('-> Get order_rs_id = ' ,req.body.order_rs_id,'-> Get order_table = ' ,req.body.order_table,'-> Get order_json = ' ,req.body.order_json);
  
   
    var order_rs_id = req.body.order_rs_id;
    var order_table = req.body.order_table;
    var order_json = req.body.order_json;
  
    var table = [order_rs_id,order_table,order_json];
    
    
    pool.getConnection(function(err, connection){
  
        connection.query("INSERT INTO nobell.order_tbl(order_rs_id, order_table, order_json, order_time) values(?,?,?,now())", table, function(err,data){
            if(err) console.log(err);
            else console.log("result", data);
            res.send(data);
            
       
        });
        connection.release();
    });
  });

  router.post('/pay', function(req, res, next) {

    console.log('-> Get customer_email = ', req.body.customer_email);
  
  
    var customer_email = req.body.customer_email;

    var table1 = [customer_email];
    
    pool.getConnection(function(err, connection){
        connection.query("UPDATE nobell.customer_tbl SET customer_state = 3 WHERE customer_email = ?", table1, function(err,data){
            if(err) console.log(err);
            else console.log("result", data);
            res.send();
       
        });
        connection.query("UPDATE nobell.table_tbl SET table_state = 2 WHERE table_customer = ?", table1, function(err,data){
            if(err) console.log(err);
            else console.log("result", data);
            res.send();
       
        });
        connection.release();
    });
  });
 
module.exports = router;