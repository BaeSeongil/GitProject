const insertForm=document.forms.insertForm;
const checkIdUrl="/product/idCheck/";
const idHelp=document.getElementById("idHelp");
const idHelp2=document.getElementById("idHelp2");
const idHelp3=document.getElementById("idHelp3");

insertForm["categoryId"].addEventListener("change",checkId);
function checkId(){
	let formCheck=false;
	let v = insertForm["categoryId"].value; //ID 입력되는 값
	console.log(v);
	if(v==1 || v==2 || v==3){
		formCheck=true;
		idHelp.style.display="inline";
		idHelp.setAttribute("name","productSize");
		idHelp2.style.display="none";
		idHelp2.removeAttribute("name");
		idHelp3.style.display="none";
		idHelp3.removeAttribute("name");
		inputColor.style.display="inline";
	}else if(v==4){
		formCheck = true;
		idHelp.style.display="none";
		idHelp.removeAttribute("name");
		idHelp2.setAttribute("name","productSize");
		idHelp2.style.display="inline";
		idHelp3.style.display="none";
		idHelp3.removeAttribute("name");
		inputColor.style.display="inline";
	}else if(v==5){
		formCheck = true;
		idHelp.style.display="none";
		idHelp.removeAttribute("name");
		idHelp2.style.display="none";
		idHelp2.removeAttribute("name");
		idHelp3.setAttribute("name","productSize");
		idHelp3.style.display="inline";
		inputColor.style.display="inline";
	}
	return formCheck; //<-async로 했기때문에 무조건 then으로 받아야함
}

insertForm.addEventListener("submit",async(e)=>{
	e.preventDefault(); //from 이벤트는 무조건 맨 위에 작성해야한다.
	let inputId = checkId();
	if(inputId){
	insertForm.submit();
	}
});