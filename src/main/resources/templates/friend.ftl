<html>
<head>
    <title>Find Friend of Friend!</title>
</head>

<h3>Find Friend, with Depth</h3>
from / to / type / degree
<form>
    <SELECT id="a">
        <option value="Richard"> Richard </option>
        <option value="Anthony"> Anthony </option>
        <option value="Mina"> Mina </option>
        <option value="Zuny"> Zuny </option>
        <option value="Marc"> Marc </option>
        <option value="MARY"> MARY </option>
        <option value="PATRICIA"> PATRICIA </option>
        <option value="LINDA"> LINDA </option>
        <option value="BARBARA"> BARBARA </option>
        <option value="ELIZABETH"> ELIZABETH </option>
        <option value="JENNIFER"> JENNIFER </option>
        <option value="MARIA"> MARIA </option>
        <option value="SUSAN"> SUSAN </option>
        <option value="MARGARET"> MARGARET </option>
        <option value="DOROTHY"> DOROTHY </option>
        <option value="LISA"> LISA </option>
        <option value="NANCY"> NANCY </option>
        <option value="KAREN"> KAREN </option>
        <option value="BETTY"> BETTY </option>
        <option value="HELEN"> HELEN </option>
        <option value="SANDRA"> SANDRA </option>
        <option value="COUPLE1"> COUPLE1 </option>
        <option value="COUPLE2"> COUPLE2 </option>
    </SELECT>
    <SELECT id="b">
        <option value="Richard"> Richard </option>
        <option value="Anthony" selected> Anthony </option>
        <option value="Mina"> Mina </option>
        <option value="Zuny"> Zuny </option>
        <option value="Marc"> Marc </option>
        <option value="MARY"> MARY </option>
        <option value="PATRICIA"> PATRICIA </option>
        <option value="LINDA"> LINDA </option>
        <option value="BARBARA"> BARBARA </option>
        <option value="ELIZABETH"> ELIZABETH </option>
        <option value="JENNIFER"> JENNIFER </option>
        <option value="MARIA"> MARIA </option>
        <option value="SUSAN"> SUSAN </option>
        <option value="MARGARET"> MARGARET </option>
        <option value="DOROTHY"> DOROTHY </option>
        <option value="LISA"> LISA </option>
        <option value="NANCY"> NANCY </option>
        <option value="KAREN"> KAREN </option>
        <option value="BETTY"> BETTY </option>
        <option value="HELEN"> HELEN </option>
        <option value="SANDRA"> SANDRA </option>
        <option value="COUPLE1"> COUPLE1 </option>
        <option value="COUPLE2"> COUPLE2 </option>
    </SELECT>
    <SELECT id="method">
        <option value="graph">Graph</option>
        <option value="hash">Hash</option>
        <option value="redis">Redis</option>
    </SELECT>
    <SELECT id="degree">
        <option value="1">First</option>
        <option value="2">Second</option>
        <option value="3">Third</option>
    </SELECT>
    <input type="button" value="show" onclick="find()">
</form>
<p id = "findResult"></p>

<br>
<br>
<h3>Who is my Friend?</h3>
<form>
    <SELECT id="user">
        <option value="Richard"> Richard </option>
        <option value="Anthony"> Anthony </option>
        <option value="Mina"> Mina </option>
        <option value="Zuny"> Zuny </option>
        <option value="Marc"> Marc </option>
        <option value="MARY"> MARY </option>
        <option value="PATRICIA"> PATRICIA </option>
        <option value="LINDA"> LINDA </option>
        <option value="BARBARA"> BARBARA </option>
        <option value="ELIZABETH"> ELIZABETH </option>
        <option value="JENNIFER"> JENNIFER </option>
        <option value="MARIA"> MARIA </option>
        <option value="SUSAN"> SUSAN </option>
        <option value="MARGARET"> MARGARET </option>
        <option value="DOROTHY"> DOROTHY </option>
        <option value="LISA"> LISA </option>
        <option value="NANCY"> NANCY </option>
        <option value="KAREN"> KAREN </option>
        <option value="BETTY"> BETTY </option>
        <option value="HELEN"> HELEN </option>
        <option value="SANDRA"> SANDRA </option>
        <option value="COUPLE1"> COUPLE1 </option>
        <option value="COUPLE2"> COUPLE2 </option>
    </SELECT>
    <input type="button" value="show" onclick="friends()">
</form>
<p id = "friends"></p>

<script type="text/javascript">
    function find() {
        var url = "/api/find?a=" + document.getElementById("a").value +
                "&b=" + document.getElementById("b").value +
                "&method=" + document.getElementById("method").value +
                "&degree=" + document.getElementById("degree").value;

        var xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                resp = JSON.parse(xmlhttp.responseText);
                document.getElementById('findResult').innerHTML =
                        "are Friend ? : " + resp.areFriend + "<br>" +
                        "Process time : " + resp.duration + " ms";
            }
        }
        xmlhttp.open("GET", url, true);
        xmlhttp.send();
    }

    function friends() {
        var url = "/api/list?user=" + document.getElementById("user").value;
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                resp = JSON.parse(xmlhttp.responseText);
                document.getElementById('friends').innerHTML = resp;
            }
        }
        xmlhttp.open("GET", url, true);
        xmlhttp.send();
    }

</script>

</html>