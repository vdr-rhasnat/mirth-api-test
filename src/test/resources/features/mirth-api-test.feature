Feature: Mirth API Test
  Scenario: JSON object test from Mirth API response
    Given HL7 message
        """
        MSH|^~\&|MHS GENESIS|0127|PARATA|NHOH Main OP|20190611143640||RDS^O01|1|T|2.3
        PID|1||G1027^^^^EPI~200857^^^^||Katie^Keller^C||19791019|F|||458 MITCHELL  ST^^BELLEVILLE^WI^53508^^^^||(408)271-9000^P^PH|||||||||||||||||N||||||||
        ORC|NW|^HNAM_ORDERID|8880|16528364^HNAM_RUNID|Ordered||^^D90^20190603070000^20200602065959^CD:230074471^^gemfibrozil 600 mg Tab 1 TID WM WITH FULL GLASS OF WATER  #90||20190611143640|C05807^Dhingra (Cerner)^Ashish^^^Cerner Dev^^^EXTERNALID^Personnel^^^External Identifier^Dhingra (Cerner)||2450453636^BROWN^KARL^L.|DONOTSEND^^^0127-0127^^PHARMACY^DONOTSEND|||Dispense|NHOH OP Pharmacy|OM||||||7300 Rdohorjjh Ibrhxbn^^Hbtkbk Oqyn^PD^86441^WK
        RXE|90^^90^20190603070000^20200602065959^Routine^^Darvocet-N 100 TABLET 1 TID WM WITH FULL GLASS OF WATER  #90|00555013906^^NDC^Darvocet-N 100 TABLET||||Tab|^(7.1.2) Take 1 tablet by mouth three times a day with meals WITH FULL GLASS OF WATER~^(7.2.2) Take 1 tablet by mouth three times a day with meals WITH FULL GLASS OF WATER||0|34|EA|03|KB1234567^BROWN^KARL^L.|C05807A^Dhingra (Cerner)^Ashish^^^Cerner Dev^^^EXTERNALID|000360120401|3|00|20190603070000|||13^Take or use exactly as directed. ZX3|Legend|60120401||||3|Y||20190611143621|Parata Max A|0002-0138346|1|1|90|NHOH Main OP|gemfibrozil|Lopid|Exelan Pharmaceuticals Inc||0|||||3RD PARTY|CD:234708423|211|0|11.63|0.13|1.75|0|0 ZAP|Legend|60120401|||||Y||211
        """
    When I hit the endpoint "http://localhost:8086/patient/"
    Then I should receive status code 200