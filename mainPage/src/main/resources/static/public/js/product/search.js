function getSearchList(){
	$.ajax({
		type: 'GET',
		url : "/getSearchList",
		data : $("form[name=search-form]").serialize(),
		success : function(result){
			//테이블 초기화
			$('#productTable > tbody').empty();
			if(result.length>=1){
				result.forEach(function(product){
					str='<tr>'
					str += "<td>"+product.productid+"</td>";
					str+="<td>"+product.productName+"</td>";
					str+="<td><a href = '/product/detail?productid=" + product.productid + "'>" + product.productName + "</a></td>";
					str+="<td>"+product.productSize+"</td>";
					str+="<td>"+product.category.categoryName+"</td>";
					str+="</tr>"
					$('#productTable').append(str);
        		})				 
			}
		}
	})
}