const replyList=document.getElementById("replyList");
setPageLing();

function setPageLing(){
	const pageLinkList=pageAjax.querySelectorAll("#pageAjax .page-link");
	pageLinkList.forEach((item)=>{
		item.addEventListener("click",replyListHadler);
	})	
}

async function replyListHadler(e){

	let url=e.target.dataset.url;
	let page=e.target.dataset.page;
	url+="/"+page;
	console.log(url);
	let res=await fetch(url);
	if(res.status==200){
		let text=await res.text();
		replyList.innerHTML=text;		
	}
	setPageLing();
}	