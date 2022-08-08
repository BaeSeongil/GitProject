const insertForm=document.forms.insertForm;
const checkIdUrl="/users/idCheck/";
const idHelp=document.getElementById("idHelp");

insertForm["userid"].addEventListener("change",checkId);
async function checkId(e){
	let formCheck=false;
	let v = insertForm["userid"].value;
	if(v.length>3){
		let res=await fetch(checkIdUrl+v);
		let idCheckJson=await res.json();
		console.log(idCheckJson);
		if(idCheckJson.idCheck){
			idHelp.style.display="none";
			insertForm["userid"].classList.remove("is-valid");
			insertForm["userid"].classList.add("is-invalid");
			idHelp.classList.remove("is-valid");
			idHelp.classList.add("is-invalid");
		}else{
			idHelp.style.display="none";
			insertForm["userid"].classList.remove("is-invalid");
			insertForm["userid"].classList.add("is-valid");
			idHelp.classList.remove("is-invalid");
			idHelp.classList.add("is-valid");
			formCheck = true;
		}
	}
	return formCheck; //<-async로 했기때문에 무조건 then으로 받아야함
}
insertForm["userpw"].addEventListener("change",checkPw);
function checkPw(){
	let v = insertForm["userpw"].value;
	let formCheck = false;
	if(v.length>2){
		formCheck=true;
		insertForm["userpw"].classList.add("is-valid");
		insertForm["userpw"].classList.remove("is-invalid");
	}else{
		insertForm["userpw"].classList.add("is-invalid");
		insertForm["userpw"].classList.remove("is-valid");
	}
	return formCheck;
}

insertForm.addEventListener("submit",async(e)=>{
	e.preventDefault(); //from 이벤트는 무조건 맨 위에 작성해야한다.
	let inputId = await checkId();
	let inputPw = checkPw();
	if(inputId && inputPw){
	insertForm.submit();
	}
});