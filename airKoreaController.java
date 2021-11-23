/*
	 * 시도별 대기정보
	 * http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty?sidoName=%EC%84%9C%EC%9A%B8&pageNo=1&numOfRows=100&returnType=json&serviceKey=kz55Vyjh95Lq9n6BSbWZ8V5d2t%2FgR8BR5j5vkXVh3%2Fej5S7DBxKoVT2OaVmPca5OXYzy5WsPyVfgxXDeTSMG8g%3D%3D
	 */
	public JSONArray getCtprvnRltmMesureDnsty(String sidoName) {

		String API_RTMDATA ="http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		String serviceKey="kz55Vyjh95Lq9n6BSbWZ8V5d2t%2FgR8BR5j5vkXVh3%2Fej5S7DBxKoVT2OaVmPca5OXYzy5WsPyVfgxXDeTSMG8g%3D%3D"; //serviceKey
		String returnType="json"; //응답타입 xml,json
		String dataTerm="DAILY"; // 데이터 기간 (1일 : DAILY, 1개월: MONTH, 3개월: 3NONTH)
		String pageNo = "1";
		String numOfRows = "100";
		//String ver=""; (규격서 참조)
		
//		String body = response.get("body").toString();

		String result=""; //JSON응답데이터
		JSONArray items = null;
		
		try {
			//API URL 설정
			URL url = new URL(API_RTMDATA+"?sidoName="+URLEncoder.encode(sidoName, "UTF-8")+"&dataTerm="+dataTerm+"&pageNo="+pageNo+"&numOfRows="+numOfRows+"&returnType="+returnType+"&serviceKey="+serviceKey);
			//API URL을 연결하여 JSON data 준비  후 READ LINE
			BufferedReader bf;
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
			
			result = bf.readLine();
			
			//JSON데이터 Read 후 items(실시간 측정정보) 파싱		
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
			JSONObject response = (JSONObject)jsonObject.get("response");
			JSONObject body = (JSONObject)response.get("body");
			items = (JSONArray) body.get("items");
		
//			
//			for (int i=0; i < items.size(); i++) {
//				System.out.println("=====items  :" + i + "========");
//				JSONObject itemsObject = (JSONObject) items.get(i);
//				
//				System.out.println(itemsObject.get("dataTime")); //측정일
//				System.out.println(itemsObject.get("mangName")); //측정망 정보
//				System.out.println(itemsObject.get("so2Value")); //아황산가스 농도
//				System.out.println(itemsObject.get("coValue")); //일산화탄소 농도
//				System.out.println(itemsObject.get("o3Value")); //오존 농도
//				System.out.println(itemsObject.get("no2Value")); //이산화질소 농도			
//				System.out.println(itemsObject.get("pm10Value")); //미세먼지(PM10)농도
//				System.out.println(itemsObject.get("pm10Value24")); //미세먼지(PM10)24시간 예측이동농도
//				System.out.println(itemsObject.get("pm25Value")); //미세먼지(PM25)농도				
//				System.out.println(itemsObject.get("pm25Value24")); //미세먼지(PM25)24시간 예측이동농도
//				System.out.println(itemsObject.get("mangName")); //측정망 정보
//				System.out.println(itemsObject.get("khaiValue")); //통합대기환경수치		
//				System.out.println(itemsObject.get("khaiGrade")); //통합대기환경지수
//				System.out.println(itemsObject.get("mangName")); //측정망 정보
//				System.out.println(itemsObject.get("so2Grade")); //아황산가스 지수				
//				System.out.println(itemsObject.get("coGrade")); //일산화탄소 지수
//				System.out.println(itemsObject.get("o3Grade")); //오존 지수
//				System.out.println(itemsObject.get("no2Grade")); //이산화질소 지수			
//				System.out.println(itemsObject.get("pm10Grade")); //미세먼지(PM10)24시간 등급
//				System.out.println(itemsObject.get("pm25Grade")); //미세먼지(PM25)24시간 등급	
//				System.out.println(itemsObject.get("pm10Grade1h")); //미세먼지(PM10)1시간 등급
//				System.out.println(itemsObject.get("pm25Grade1h")); //미세먼지(PM25)1시간 등급
//				System.out.println(itemsObject.get("so2Flag")); //아황산가스 플래그
//				System.out.println(itemsObject.get("coFlag")); //일산화탄소 플래그				
//				System.out.println(itemsObject.get("o3Flag")); //오존 플래그
//				System.out.println(itemsObject.get("no2Flag")); //이산화질소 플래그
//				System.out.println(itemsObject.get("pm10Flag")); //미세먼지(PM10)플래그
//				System.out.println(itemsObject.get("pm25Flag")); //미세먼지(PM25)플래그				
// 				
//			}

		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}

	public HashMap<String, Object> getCvtSido(JSONArray items) {
	 	
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		int maxPm10Val = 0;
		String sidoName = null;
		String dataTime = null;
		 if(items.size() > 0 && items != null) {

				for(int i = 0 ; i < items.size(); i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
		    		JSONObject itemsObject = (JSONObject) items.get(i);
		    		sidoName = itemsObject.get("sidoName").toString();
		    		String stationName = itemsObject.get("stationName").toString();
		    		dataTime = itemsObject.get("dataTime").toString();
		    		String pm10Value = itemsObject.get("pm10Value").toString();
//		    		int num1 = Integer.parseInt(stationName);
//		    		int num2 = Integer.parseInt(pm10Value);
		    		if(!itemsObject.get("pm10Value").equals("-")) {
		    			map.put("pm10Value", pm10Value);
		    			map.put("sidoName", sidoName);
		    			map.put("stationName", stationName);
		    			map.put("dataTime", dataTime);
		    			int pm10 = Integer.parseInt(pm10Value);
		    			maxPm10Val += pm10;
		    			map.put("maxPm10Val", maxPm10Val);
		    		}
		    		
		    		list.add(map);
//		    		value += sidoName+" 미세먼지 정보를 알려드릴께요"+ sidoName+" 현재시간"+dataTime+" 미세먼지"+ + "^"+ stationName + "^IN"; 
				}
				int dvsPm10Val2 =  maxPm10Val / list.size();
				
				String dvsPm10Val =Integer.toString(dvsPm10Val2);
				LOGGER.info("dvsPm10Val ======> " + dvsPm10Val);
				map2.put("sidoName", list.get(0).get("sidoName"));
				map2.put("dataTime", list.get(0).get("dataTime"));
				map2.put("dvsPm10Val", dvsPm10Val);
				
//				for (int i = 0; i < list.size(); i++) {
//					
//						int pm10Val = (int) list.get(i).get("pm10Value");
//						maxPm10Val += pm10Val;
//					
//				}
				
		 }
		 return map2;
	 }