$(document).ready(tableDataRequest);

poeClassDataRequest();
versionDataRequest();

var h = $(location).attr('host');

$("#poeClasses").on('change',tableDataRequest);
$("#version").on('change',tableDataRequest);

function poeClassDataRequest(){
    $.ajax({
        url: getHost() + "/classes",
        async : false
        })
        .then(fillPoeClassSelect);
}

function versionDataRequest(){
    $.ajax({
        url: getHost() + "/versions",
        async : false
        })
        .then(fillVersionSelect);
}

function tableDataRequest() {
    var className = $("#poeClasses").find(":selected").val();
    var version = $("#version").find(":selected").val();
    $.ajax({
        url: getHost() + "/builds?version=" + version + "&poeClass=" + className,
        async : false
        })
        .then(fillTable);
}

function fillTable(data) {
    $("#buildsTableBody").empty();
    $.each(data,function(key,$datum){
        $htmlstring ="<tr>"+
            "<td>"+"<a href='https://www.pathofexile.com" + data[key].url  + "' target='_blank'>"
            + data[key].name+"</a></td>"+
            "<td>"+data[key].views+"</td>"
            +"</tr>";
        $("#buildsTableBody").append($htmlstring);
    });
}

function fillPoeClassSelect(data) {
    $("#poeClasses").empty();
    $.each(data,function(key,$datum){
        var name = data[key].name;
        $htmlstring = "<option value=" + name + ">" + name + "</option>";
        $("#poeClasses").append($htmlstring);
    });
}

function fillVersionSelect(data) {
    $("#version").empty();
    $.each(data,function(key,$datum){
        $htmlstring = "<option value=" + data[key] + ">" + data[key] + "</option>";
        $("#version").append($htmlstring);
    });
}

function getHost(){
    return $(location).attr('protocol') + "//" + $(location).attr('host');
}