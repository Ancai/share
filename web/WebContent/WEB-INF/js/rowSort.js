//表格的行排序
   var isUp = false;
   var compareUp = function(val1, val2) {
	   if (val1 < val2) {
		   return -1;
	   } else if (val1 > val2) {
		   return 1;
	   } else {
		   return 0;
	   } 
   };
   var compareDown = function(val1, val2) {
	   if (val1 < val2) {
		   return 1;
	   } else if (val1 > val2) {
		   return -1;
	   } else {
		   return 0;
	   } 
   };
   function sortrows(n) {
       var tbody = $(".datagrid-body table tbody:first")[0];
   var rows = $(".datagrid-body table tbody:first tr");			
   rows = Array.prototype.slice.call(rows, 0);
   if (isUp) {
	   isUp = false; 
	   rows.sort(function(row1, row2) {
           var cell1 = row1.getElementsByTagName("td")[n];
           var cell2 = row2.getElementsByTagName("td")[n];
           var val1 = cell1.textContent || cell1.innerText;
           var val2 = cell2.textContent || cell2.innerText;
           return compareDown(val1, val2);
       }); 
   } else {
	   isUp = true;
	   rows.sort(function(row1, row2) {
           var cell1 = row1.getElementsByTagName("td")[n];
           var cell2 = row2.getElementsByTagName("td")[n];
               var val1 = cell1.textContent || cell1.innerText;
               var val2 = cell2.textContent || cell2.innerText;
               return compareUp(val1, val2);
           });   
	   }
       
       for ( var i = 0; i < rows.length; i++) {
    	   tbody.appendChild(rows[i]);
	   }
   }

   function makeSortable() {
       var headers = $(".datagrid-header-inner table tbody tr:first td");
       for ( var j = 0; j < headers.length; j++) {
           (function(n) {
               headers[j].onclick = function () { sortrows(n); };
           }(j));
	   }
   }
   
   makeSortable();