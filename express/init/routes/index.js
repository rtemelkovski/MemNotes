var express = require('express');
var router = express.Router();

var bodyParser = require('body-parser');

//MONGO DATABASE NEW PATIENTS NOT VERIFIED WHILE VERIFIED PATIENTS SHOWN (NEEDS TO A BOOLEAN VALUE)



/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

/* POST LOGIN */
router.post('/login', function(req, res, next){
	var usr = req.body.usr;
	var pass = req.body.pas;
	console.log(usr);
	console.log(pass);
	//check if the username and password are in the database
	res.send("TRUE");
	//append username and password together something
});

/* GET PATIENTS */ 
router.get('/getPatients', function(req, res, next){
	//Get shit from mongo
	//Get Mongo ids of all patients
	//Get the name stored in each document matching the ids
	//res.send(the mongo info);
	});

/* POST PATIENT */
router.post('/patient', function(req, res, next){
	var patient = req.body.pid;
	//Send id, get all the information
});

/* POST NEWPATIENT */
router.post('/newPatient', function(req, res, next){
	var gcmToken = req.body.token;
	var caregiverID = req.body.cid;
	var name = req.body.patient_name;
	var number = req.body.patient_number;
	var address = req.body.patient_address;
	var ename = req.body.contact_name;
	var enumber = req.body.contact_number;
	var password = req.body.password;
	//Create new document in mongo database, store gcm and name
});

/* POST VERIFYPATIENT */
router.post('/verifyPatient', function(req, res, next){
	var room = req.body.roomnumber;
	var allergies = req.body.allergy;
	var meds = req.body.medication;
	var notes = req.body.notes;

})

/* GET LOGIN */
router.post('/login', function(req, res, next){
	res.send("FUCK YOU");
});

module.exports = router;
