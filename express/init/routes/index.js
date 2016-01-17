
var express = require('express');
var router = express.Router();

var bodyParser = require('body-parser');
var mongoose = require('mongoose');
mongoose.connect('mongodb://PavleArezina:Kronoros1@ds047365.mongolab.com:47365/deltahacks');
var db = mongoose.connection;
var ObjectId = require('mongoose').Types.ObjectId; 
db.on('error', console.error.bind(console, 'connection error'));
db.once('open', function (callback) {
    console.log('connected');
});

var PatientSchema = new mongoose.Schema({
	gcmToken: String,
	caregiverID: String,
    firstname: String,
    lastname: String,
    number: String,
    address: String,
    ename: String,
    enumber: String,
    gender: String,
    room: String,
    allergies: String,
    meds: String,
    notes: String,
    isConfirmed: Boolean
});

var Patientz = mongoose.model('Patients', PatientSchema);


//MONGO DATABASE NEW PATIENTS NOT VERIFIED WHILE VERIFIED PATIENTS SHOWN (NEEDS TO A BOOLEAN VALUE)


var newPatient = new Patientz({ 
	gcmToken: "yolo",
	caregiverID: "Roberto",
    firstname: "Teo",
    lastname: "Focker",
    number: "34543534",
    address: "1280 Main",
    ename: "Roberto",
    enumber: "Whipped",
    gender: "Questioning",
    room: "666",
    allergies: "Everything",
    meds: "Heroin",
    notes: "Watch 24/7",
    isConfirmed: true });
newPatient.save(function (err, newPatient) {
  if (err) return console.error(err);
});

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
	Patientz.find({},
        function (err, patients) {
        	var x = new Array(patients.length);
			for (var i = 0; i < patients.length; i++) {
  				x[i] = new Array(7);
			}
			console.log(x.length);	
            for (i = 0; i < patients.length; i++) { 
    			x[i][0]=patients[i]._id;////////////////////////////////////////GET PROPER ID
    			x[i][1]=patients[i].firstname;
    			x[i][2]=patients[i].lastname;
    			x[i][3]=patients[i].room;
    			x[i][4]=patients[i].allergies;
    			x[i][5]=patients[i].meds;
    			x[i][6]=patients[i].notes;
			}
			res.send(JSON.stringify(x));
        });
	});

/* POST PATIENT */
router.post('/patient', function(req, res, next){
	//from bson.objectid import ObjectId
	var patient = req.body.pid;
	console.log(patient);
	Patientz.findById(patient, function (err, id) {
		res.send(id);
	});
});

/* POST NEWPATIENT */
router.post('/newPatient', function(req, res, next){
	var gcmToken1 = req.body.token;
	var caregiverID1 = req.body.cid;
	var firstname1 = req.body.first_name;
	var lastname1 = req.body.last_name;
	var number1 = req.body.number;
	var address1 = req.body.address;
	var ename1 = req.body.contact_name;
	var enumber1 = req.body.contact_number;
	var gender1 = req.body.gender;
	var newPatient1 = new Patientz({ 
		gcmToken: gcmToken1,
		caregiverID: caregiverID1,
    	firstname: firstname1,
    	lastname: lastname1,
    	number: number1,
    	address: address1,
    	ename: ename1,
    	enumber: enumber1,
    	gender: gender1,
    	isConfirmed: false });
	newPatient1.save(function (err, newPatient1) {
  		if (err) return console.error(err);
	});
	//Create new document in mongo database, store gcm and name
	res.send({response: "OKAY"});
});
/* GET NEWPATIENTS */
router.get('/patientsVerify', function(req, res, next){
	Patientz.find({isConfirmed : false},
        function (err, verify) {
        	res.send(verify);
	});
})
/* POST VERIFYPATIENT */
router.post('/verifyPatient', function(req, res, next){

	var room1 = req.body.roomnumber;
	var allergies1 = req.body.allergy;
	var meds1 = req.body.medication;
	var notes1 = req.body.notes;
	db.patients.update({})
	var newPatient = new Patientz({ 
    room: room1,
    allergies: allergies1,
    meds: meds1,
    notes: notes1,
    isConfirmed: true });
	newPatient.save(function (err, newPatient) {
  		if (err) return console.error(err);
	});
	//Store important info to the user
})

/* POST NOTIFICATION */
router.post('/notification', function(req, res, next){
	//gcm token used here
	//send a string as a json object
	//
});

module.exports = router;
