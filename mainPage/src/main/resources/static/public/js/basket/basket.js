/**
 * 
 */
 function selectAll(thisVal)  {
 	 let val=thisVal.checked;
	  const checkboxes = document.getElementsByName('basket_id');
	  
	  checkboxes.forEach((checkbox) => {
	
	    checkbox.checked = val;
	  })
	}