var val = $("#orderId").val();
gantt.config.xml_date = "%Y-%m-%d %H:%i";
gantt.config.duration_unit = "hour";
gantt.config.columns = [
    {name: "text", tree: true, width: '*', resize: true},
    {name: "start_date", align: "center", resize: true},
    {name: "duration", align: "center"}
]
gantt.load("/manager/gantt/"+val);
gantt.init("gantt_here");
