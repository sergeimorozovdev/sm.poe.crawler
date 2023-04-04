$.ajax({
        url: "http://localhost:8080/builds?version=3.20"
    }).then(fillTable);

$("#poeClasses").on('change',
function classChose(className) {
  var className = $(this).find(":selected").val();
  $.ajax({
          url: "http://localhost:8080/builds?version=3.20&poeClass=" + className
      }).then(fillTable);
});

function fillTable(data) {
       $("#buildsTableBody").empty();
       $.each(data,function(key,$datum){
            $htmlstring ="<tr>"+
                  "<td>"+"<a href='https://www.pathofexile.com" + data[key].url  + "' target='_blank'>"
                  + data[key].name+"</a></td>"+
                   "<td>"+data[key].views+"</td>"+
                   "<td>"+data[key].author+"</td>"
                   +"</tr>";
            $("#buildsTableBody").append($htmlstring);
        });
    }
