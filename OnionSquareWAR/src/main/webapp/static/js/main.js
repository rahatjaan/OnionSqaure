 function countryChange(){
		 $.ajax({
			   headers: { "Content-Type": "application/json", "Accept": "application/json" },
			   type:"GET",
			   url: "<c:url value='/stateList'/>",			   
			   data: {"countryId":$("#countryId").val()},
			   dataType: "json",
			   success: function(data){
				  
				   var html = "<option value=''>Select</option>";
				   
					if (data) {
						for (p in data) {
							
								html += "<option value='" + p + "'>" + data[p] + "</option>";
						
							
						}
					}
					$("#stateId option").remove();
					$("#stateId").append(html).trigger("change");
				   
			   } ,
			     error:function(x,e){
			         
			        alert("Ajax error");
			        }
			   });       
	 }