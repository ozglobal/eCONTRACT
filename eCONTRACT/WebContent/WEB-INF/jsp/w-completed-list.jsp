<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:url value="/completed-list/read" var="completed_list_read_url" />

<div id="completedcontract_list"></div>
<div id="pdfWnd"></div>

<script>
	var user_role  = "${model.user_role}";
	
	$(document).ready(function () {
		var tmpVal = '';
		
		var today = new Date();
		/* var dd = today.getDate();
		var mm = today.getMonth()+1; //January is 0!
		var yyyy = today.getFullYear();

		if(dd<10) {
		    dd='0'+dd
		} 

		if(mm<10) {
		    mm='0'+mm
		} 

		today = mm+'/'+dd+'/'+yyyy; */
		
		var dataSource = new kendo.data.DataSource({
			transport: {
	          	read : {
					url : "${completed_list_read_url}",
					dataType : "json",
					type : "POST",
					contentType : "application/json"
				},
	            parameterMap: function(options, type) {
	            	if(type == 'read'){
						if ($('#startDate').val() !== null && $('#startDate').val() !== "") {
							options.start = $('#startDate').val();
							options.end = $('#endDate').val();
						} else {// Show today joblist as default
							$("#startDate").val(today.toISOString().substring(0, 10));
							$("#endDate").val(today.toISOString().substring(0, 10));
							
							options.start = today.toISOString().substring(0, 10);
							options.end = today.toISOString().substring(0, 10);
						}	
					}
					if(type == 'update'){						
					}
					if(type == 'destroy'){						
					}
					return JSON.stringify(options);
	            },
			},
			requestEnd: function(e) {				
			    if (e.type == "update") {
			    	$("#completedcontract_list").data("kendoGrid").dataSource.read();
			    }
			    
			    if(e.type == undefined && !(e.response instanceof Array)) {
			    	$("#completedcontract_list").data("kendoGrid").dataSource.remove($("#completedcontract_list").data("kendoGrid").dataSource.at(0));
			    }
			},
	        schema: {
	            model: {
	                id: "contract_id",
	                fields: {
	                	contract_id :{ type: "string" , validation: { required: true }},	                	
	                	first_name :{ type: "string" , validation: { required: true }},
	                	prod_no :{ type: "string"},
	        			email :{ type: "string" , validation: {
	        				customValidation: function(input, params) {
                                 if (input.is("[name=email]")) {
                                	 var reg_email=/^[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[@]{1}[-A-Za-z0-9_s]+[-A-Za-z0-9_.]*[.]{1}[A-Za-z]{2,5}$/;
                                     var email = input.filter("[name='email']").val()
              
                                     if (email.search(reg_email) == -1) {
	                                     $(input).attr("data-customValidation-msg", "The e-mail is not valid");
	                                     return false;
    	                             }
                                 }
                                 //check for the rule attribute
                                 return true;
                               }	
	        				}
	        			},
	        			updated_date : {
	        				type: "datetime"
	        			}
	                }
	            }
	        },
			//sort: {field: "updated_date", dir: "desc"},
	        pageSize: 15
	    });
		
		$("#completedcontract_list").kendoGrid({
			toolbar : [{ template: '#=toolbarDisplay()#'}],
			dataBound: function(){
				//Hide show columns
				var grid = $('#completedcontract_list').data('kendoGrid');
			    if(user_role == "ROLE_TELLER") {
			    	grid.hideColumn("branch_name");
			    	grid.hideColumn("prod_no");
			    	grid.hideColumn("user_id");
			    } else if (user_role == "ROLE_REPORTER") {
			    	grid.hideColumn("first_name");
			    } else if (user_role == "ROLE_MANAGER") {
			    	grid.hideColumn("first_name");
			    } else if (user_role == "ROLE_CONTRACT") {
			    	grid.hideColumn("first_name");
			    	grid.hideColumn("action_cmd");
			    } else if (user_role == "ROLE_ADMIN") {
			    	grid.hideColumn("action_cmd");
			    }
		        
				if (this.dataSource.view().length == 0) {
		        	var currentPage = this.dataSource.page();
		        	if (currentPage > 1) {
		            	this.dataSource.page(currentPage - 1);
		        	}
		    	}
			},
			filterable: {
	             mode: "row"
	        },
			scrollable: true,
			selectable: true,
			sortable: true,
			pageable: true,
			resizable: true,
			dataSource: dataSource,
			columns: [{ title: "No.",width:20, template: "#=getTemplate('completedcontract_list', data)#"},
				{ title: "Гэрээний ID", field: "contract_id",
				  filterable: {
						cell: {
							showOperators: false,
							operator: "contains"
						}
					},
				},
				{ title: "Гэрээний төрөл", field: "contract_type",
				  filterable: {
						cell: {
							showOperators: false,
							operator: "contains"
						}
					},
				},
				{ title: "Салбар", field: "branch_name",
				  filterable: {
						cell: {
							showOperators: false,
							operator: "contains"
						}
					},
				},	
				{ title: "Хэрэглэгчийн нэр", field: "first_name",
				  filterable: {
						cell: {
							showOperators: false,
							operator: "contains"
						}
					},
				},
				{ title: "Дугаар", field: "product_number",
				  filterable: {
						cell: {
							showOperators: false,
							operator: "contains"
						}
					}
				},
				{ title: "Имэйл хаяг", field: "email", 
					filterable: {
						cell: {
							showOperators: false,
							operator: "contains"
						}
					},
				},
				{ title: "Гэрээний төлөв", field: "contract_status_name", 
					filterable: {
						cell: {
							showOperators: false,
							operator: "contains"
						}
					},
				},
				{ title: "Үйлчилгээний ажилтан", field: "user_id", 
					filterable: {
						cell: {
							showOperators: false,
							operator: "contains"
						}
					},
				},
				{ title: "Огноо", field: "updated_date",filterable: false, format: "{0:yyyy-MM-dd HH:mm:ss}"},
				{ title: "Файл", field: "pdf_file_name", 
					attributes: {
						"class": "table-cell",
						style: "text-align: center; vertical-align: middle;"
					}, 
					width: 40,
					filterable: false,
					template:"#=pdf_file_name_template#"
				},
				{ field: "action_cmd", filterable: false,
					command: [{name: "Contract",
							template:"<a class='k-button k-button-icontext k-grid-Contract' href='javascript:;'></a>",
							click:function(e){
								var tr = $(e.target).closest("tr"); // get the current table row (tr)
								var data = this.dataItem(tr);
								if(data.contract_status != "C") { // If contract status is not completed
									location.href="./w-application-ozviewer?prod_no=" + data.product_number + "&reportname=" + data.ozd_file_name;	
								} else if(data.contract_status == "C" && (user_role == "ROLE_REPORTER" || user_role == "ROLE_MANAGER")) {
									location.href="./w-application-ozviewer?prod_no=" + data.product_number + "&reportname=" + data.ozd_file_name;
								} else {
									alert("Гэрээг баталгаажуулсан тул засварлах боломжгүй.");
									/* alert ("This contract form was completed. You can not update it!"); */
								}
								
							}
						  }
						],
					  title: " ",
					  width: 60
				},
				{ title: "И-мэйл илгээх", field: "is_email_sent", 
					attributes: {
						"class": "table-cell",
						style: "text-align: center; vertical-align: middle;"
					}, 
					width: 60,
					filterable: false,
					template:"#=is_send_email_template#"
				}]	
		});
		
		$(".termSearch").click(function() {
			$("#completedcontract_list").data('kendoGrid').dataSource.read();
		});
		
		$("#startDate").kendoDateTimePicker({
		    format: "yyyy-MM-dd"
		});
		
		$("#endDate").kendoDateTimePicker({
		    format: "yyyy-MM-dd"
		});
		
		$(".k-filtercell").find("[data-role=datepicker]").kendoDatePicker("setOptions", {
			format: "yyyy-MM-dd"
      	});
	});
		
	function openPDF(fileName, prodNo) {
		
		var url = "./download-file/ftp/w";
		
		var form = document.createElement("form");
		
		$('.PdfViewClass').remove();

	    form.setAttribute("name", "pdfView_" + prodNo);
	    form.setAttribute("id", "pdfView_" + prodNo);
	    form.setAttribute("class", "PdfViewClass");
		
        var prodNoField = document.createElement("input");
        prodNoField.setAttribute("type", "hidden");
        prodNoField.setAttribute("name", "prodNo");
        prodNoField.setAttribute("value", prodNo);
        
        var pdfFileNameField = document.createElement("input");
        pdfFileNameField.setAttribute("type", "hidden");
        pdfFileNameField.setAttribute("name", "pdffilename");
        pdfFileNameField.setAttribute("value", fileName);
        
        form.appendChild(prodNoField);
        form.appendChild(pdfFileNameField);
	    
	    document.body.appendChild(form);
	 	
	    var _pdfView = document.getElementById("pdfView_" + prodNo);
	    window.open("", "ContractView", 'toolbar=no, location=no, status=no, menubar=no, scrollbars=yes'); 
	    _pdfView.action = url; 
	    _pdfView.method = "POST";
	    _pdfView.target = "ContractView";
	    _pdfView.submit();	    
	}
	
	function sendEmail(id, customerEmail) {
		
		var cEmail = prompt("Please confirm below customer email.", customerEmail);
		if (cEmail !== null) {
			$("#completedcontract_list").fadeTo("slow", 0.15);

			var formdata = new FormData();
			formdata.append("id", id);
			formdata.append("customer_email", cEmail);
			var xhr = new XMLHttpRequest();       
			xhr.open("POST", "./send-email", true);
			xhr.send(formdata);
			xhr.onload = function(e) {
				if (this.status == 200) {
				   alert("Send Email Success.");
				   $("#completedcontract_list").fadeTo("slow", 1);
				} else {
				   alert(this.responseText);
				   $("#completedcontract_list").fadeTo("slow", 1);
				}
			};
		}
	}
	
	//function toolbarDisplay(taskType) {
	function toolbarDisplay() {
		var _div = document.createElement("div");
		var a_pdf = document.createElement("a");
		a_pdf.className = "k-button checkBtn";
		//a_pdf.setAttribute("id", "checkBtn" + taskType);
		a_pdf.setAttribute("id", "checkBtn");
		a_pdf.style.marginRight = "900px";
				
		var input_searchFrom = document.createElement("input");
		//input_searchFrom.setAttribute("id", "startDate" + taskType);
		input_searchFrom.setAttribute("id", "startDate");
		input_searchFrom.style.marginLeft = "10px";
		input_searchFrom.style.marginRight = "10px";
		input_searchFrom.style.width = "200px";
		
		var input_searchTo = document.createElement("input");
		//input_searchTo.setAttribute("id", "endDate" + taskType);
		input_searchTo.setAttribute("id", "endDate");
		input_searchTo.style.marginLeft = "10px";
		input_searchTo.style.marginRight = "10px";
		input_searchTo.style.width = "200px";
		
		var a_search = document.createElement("a");
		a_search.className = "k-button termSearch";
		//a_search.setAttribute("id", "termSearch" + taskType);
		a_search.setAttribute("id", "termSearch");
		
		//var node = document.createTextNode("PDF DownLoad");
		var node1 = document.createTextNode("From:");
		var node2 = document.createTextNode("To:");
		var node3 = document.createTextNode("Search");
		
		//a_pdf.appendChild(node);
		a_search.appendChild(node3);
		//_div.appendChild(a_pdf);
		_div.appendChild(node1);
		_div.appendChild(input_searchFrom);
		_div.appendChild(node2);
		_div.appendChild(input_searchTo);
		_div.appendChild(a_search);
		return _div.innerHTML;
	}
	
</script>


<style scoped="scoped">
div.k-widget.k-upload.k-header
{
    width: 250px;
    display: inline-block;
}

.k-callout-n{
	left : 0;
}

div.k-widget.k-window,
.k-edit-form-container
{
	min-width:"";
    width: 900px;
}
.k-edit-label
{
    width: 20%;
}
.k-edit-field,
.k-input.k-textbox
{
    width: 75%;
}
.pdf_button {
		background: url(./resources/images/pdf.png) no-repeat;
		border: none;
		width: 20px;
		height: 20px;
		cursor: pointer;
		background-position:center center;
		vertical-align: middle;
}
.sendEmail_button {
		background: url(./resources/images/send-email.png) no-repeat;
		border: none;
		width: 20px;
		height: 20px;
		cursor: pointer;
		background-position:center center;
		vertical-align: middle;
}
.sendEmail_button_y {
		background: url(./resources/images/send-email-y.png) no-repeat;
		border: none;
		width: 20px;
		height: 20px;
		cursor: pointer;
		background-position:center center;
		vertical-align: middle;
}
.k-button.k-button-icontext.k-grid-edit{
	background: url(./resources/images/edit.png) no-repeat;
	background-position:center center;
	min-width:30px;
	height:32px;
	border:white;
}
.k-button.k-button-icontext.k-grid-delete{
	background: url(./resources/images/delete.png) no-repeat;
	background-position:center center;
	min-width:30px;
	height:32px;
	border:white;
}
.k-button.k-button-icontext.k-grid-JobHistory{
	background: url(./resources/images/job-history.png) no-repeat;
	background-position:center center;
	min-width:30px;
	height:32px;
	border:white;
}
.k-button.k-button-icontext.k-grid-Contract{
	background: url(./resources/images/contract.png) no-repeat;
	background-position:center center;
	min-width:30px;
	height:32px;
	border:white;
}

.k-upload-button{
	min-width: inherit;
}

.k-window-titlebar.k-header {
	padding: 0;
	text-align: center;
	line-height:44px;
	height: 44px;
	color: white;
	background-color:#333a4d;
	font-size: 23px;
	font-weight: bold; /*set font-weight to the titlebar*/
}
div.k-widget.k-window{
	  border-width: 0;
}

/* close icon */
.k-window-actions .k-i-close
{
    background: url(./resources/images/close.png) no-repeat; 
	margin-left: -15px;
	margin-top: -10px;
	width: 21px;
	height: 21px;
}

a.k-window-action.k-link.k-state-hover
{
    background: url(./resources/images/close.png) no-repeat; 
    margin-left: -33px;
	margin-top: -12px;
	width: 21px;
	height: 21px;
    border: 0;
}


/* .k-grid-header th.k-header>.k-link { */
/* 	white-space: normal; */
/* } */
</style>
