var val = $("#orderId").val();
gantt.config.xml_date = "%Y-%m-%d %H:%i";
gantt.config.duration_unit = "hour";
gantt.load("/manager/gantt/"+val);
gantt.init("gantt_here");

