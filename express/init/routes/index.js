
var express = require('express');
var gcm = require('node-gcm');
var router = express.Router();

var bodyParser = require('body-parser');
var mongoose = require('mongoose');
mongoose.connect('mongodb://PavleArezina:Kronoros1@ds047365.mongolab.com:47365/deltahacks');
var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error'));
db.once('open', function (callback) {
    console.log('connected');
});
var globalCount= 0;
var globalCaregiverID=0;
var CaregiverSchema = new mongoose.Schema({
	user : String,
	pass : String,
	idd : String
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
var Caretaker = mongoose.model('Caretakers', CaregiverSchema);
var Patientz = mongoose.model('Patients', PatientSchema);


//MONGO DATABASE NEW PATIENTS NOT VERIFIED WHILE VERIFIED PATIENTS SHOWN (NEEDS TO A BOOLEAN VALUE)

var newCaretaker = new Caretaker({
	user : "Teo",
	pass : "password",
	idd : globalCount
});
newCaretaker.save(function (err, newPatient) {
  if (err) return console.error(err);
});
globalCount++;
var newCaretaker = new Caretaker({
	user : "Pavle",
	pass : "password",
	idd : globalCount
});
newCaretaker.save(function (err, newPatient) {
  if (err) return console.error(err);
});


var newPatient = new Patientz({ 
	gcmToken: "yolo",
	caregiverID: 0,
    firstname: "YOLLLLO",
    lastname: "HEHEHEHEH",
    number: "111111111",
    address: "1280 ",
    ename: "yo",
    enumber: "yo",
    gender: "Male",
    room: "111",
    allergies: "FIRE A GUN",
    meds: "MATE",
    notes: "NIGHT WAS WARM",
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
	var password = req.body.pas;
	Caretaker.find({user : usr, pass : password},
        function (err, verify) {
        	if (!verify[0])
        	{
        		res.send("FALSE");
        	}
        	else
        	{
        		globalCaregiverID=verify[0].idd;
        		res.send("TRUE");
        	}
	});
});
///////////////////////////////////////////IF WE HAVE TIME DO REGISTER CARETAKERS
/* GET PATIENTS */ 
router.get('/getPatients', function(req, res, next){
	Patientz.find({caregiverID : globalCaregiverID, isConfirmed : true},
        function (err, patients) {
        	var x = new Array(patients.length);
			for (var i = 0; i < patients.length; i++) {
  				x[i] = new Array(7);
			}	
            for (i = 0; i < patients.length; i++) { 
    			x[i][0]=patients[i]._id;
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
	var patient = req.body.pid;
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
	Patientz.find({isConfirmed : false, caregiverID : globalCaregiverID},
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
	var patient = req.body.pid;
	Patientz.findByIdAndUpdate(patient, { $set: { allergies : allergies1, meds : meds1, room : room1, notes : notes1, isConfirmed : true }}, function (err, tank) {
  		if (err) return handleError(err);
  		res.send(tank);
	});
})

/* POST ACHECK */
router.post('/aCheck', function(req, res, next){
	var firstname1 = req.body.first_name;
	console.log(firstname1);
	var lastname1 = req.body.last_name;
	var gcmToken1 = req.body.token;
	Patientz.find({firstname : firstname1, lastname : lastname1, gcmToken : gcmToken1},
        function (err, verify) {
        	res.send(verify[0]);
	});
});
/* POST NOTIFICATION */
router.post('/notification', function(req, res, next){
	var messaging = req.body.message;
	var token = req.body.gcmToken;
	
	var message = new gcm.Message();
	var sender = new gcm.Sender('AIzaSyCslqmnFUXjXfsjubtneeNXV107CLD9rYI');
	var registrationIds = [];
 
	message.addData('title','MemNote');
	message.addData('message', messaging);
	message.addData('msgcnt','1');
	message.delayWhileIdle = true;
	message.timeToLive = 3;

	registrationIds.push(token);

	sender.send(message, registrationIds, 4, function (err, result) {
	});
	res.send({response: "OKAY"});
});

module.exports = router;
