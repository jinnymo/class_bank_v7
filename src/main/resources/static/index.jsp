
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<title>간단한 지도 표시하기</title>
<script type="text/javascript" src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=v902umqlgq"></script>
</head>
<body style="display: flex; justify-content: center;">

	<div id="textarea" style="width: 50%; height: 100vh;">
	<button onclick="fetchdata()">통신요청</button>
	
	</div>
	<div id="map" style="width: 50%; height: 100vh;"></div>
	
	<script>
	
	
		
		var textElement = document.getElementById("textarea");
		var markerList = [];
		var infoWindows = [];
	
		var map = new naver.maps.Map('map', {
			zoom : 17,
			center : new naver.maps.LatLng(35.15960017, 129.06018048),
			padding : {
				top : 100
			},
			zoomControl : true,
			zoomControlOptions : {
				style : naver.maps.ZoomControlStyle.SMALL
			}
		});
		
		function fetchdata(){
            fetch('http://localhost:8080/test1')
            .then(data=>{
                console.log("then() 수행 1");
                console.log("data : " + data);
                return data.json();
            })
            .then(data2=>{
                for ( let i=0, ii = data2.length; i<ii; i++ ){
                    console.log(data2[i].name);
                    console.log(data2[i].name);
                    var marker = new naver.maps.Marker({
                        position : new naver.maps.LatLng( (data2[i].y, (data2[i].x),
                        map : map,
                        icon : {
                            content : '<p style="color: red; font-weight: bolder;">'
                                    + data2[i].id + '</p> ',
                            size : new naver.maps.Size(22, 35),
                            anchor : new naver.maps.Point(11, 35)
                        }
                    }

                }

            })
            .catch(error=>{
                console.log("eroor:"+error);
            })

            }
		
	

		naver.maps.Event.addListener(map, 'click', function(e) {
			
			var marker = new naver.maps.Marker({
				position : e.coord,
				map : map,
				icon : {
					content : '<p style="color: red; font-weight: bolder;">' + count + '</p> ',
					size : new naver.maps.Size(22, 35),
					anchor : new naver.maps.Point(11, 35)
				}
			});
			markerList.push(marker);
			//const card = createdata();
			//textElement.append(card);
			
			console.log(e.coord.y);
			console.log(e.coord.x);
			count += 1;
			
			/* function createdata(){
				  const card = document.createElement('div');
				  card.className = "card";
				  
				  card.innerHTML = `
					    <form action="/서버root/MapController/postgis" method="post">
				        
				        <input type="text" name="insertValue">
				        <input type="submit" value="전송">
				        
				    </form>
				    <input type="submit" value="삭제">
				  `;
				  retrun card;
			/*  textElement.innerHTML += "<div>" + "('Node" + count + "',"
					+ "ST_SetSRID(ST_MakePoint(" + e.coord.x + "," + e.coord.y
					+ "),4326)),<div>"; 
			
			} */
		});
		
		/* function getClickHandler(seq) {
		    return function(e) {
		        var marker2 =  markerList[seq],
		            infoWindow = infoWindows[seq];

		        if (infoWindow.getMap()) {
		            infoWindow.close();
		        } else {
		            infoWindow.open(map, marker2);
		    	}
		        console.log(infoWindow)
		     }
		}
		
		//일회성 코드 각 마커에 이벤트 리스터 추가 나중에 코드 수정 필요 
		for (var i=0, ii=markerList.length; i<ii; i++) {
		  naver.maps.Event.addListener(markerList[i], 'mouseover', getClickHandler(i));
		//  naver.maps.Event.addListener(markerList[i], 'mouseout', mouseOut(seq));
			
		 
		} */
		
		
		var contentEl = $('<div class="iw_inner" style="width:350px;position:absolute;top:0;right:0;z-index:1000;background-color:#fff;border:solid 1px #333;">'
				+ '<h3>Map States</h3>'
				+ '<p style="font-size:14px;">zoom : <em class="zoom">'
				+ map.getZoom()
				+ '</em></p>'
				+ '<p style="font-size:14px;">centerPoint : <em class="center">'
				+ map.getCenterPoint() + '</em></p>' + '</div>');

		contentEl.appendTo(map.getElement());

		naver.maps.Event.addListener(map, 'zoom_changed', function(zoom) {
			contentEl.find('.zoom').text(zoom);
		});

		naver.maps.Event.addListener(map, 'bounds_changed', function(bounds) {
			contentEl.find('.center').text(map.getCenterPoint());
			console.log('Center: ' + map.getCenter().toString() + ', Bounds: '
					+ bounds.toString());
		});
		
		
		function addDiv() {
			
		}
		
		
		
		
		
	</script>

</body>
</html>