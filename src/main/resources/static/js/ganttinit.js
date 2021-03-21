gantt.config.xml_date = "%Y-%m-%d %H:%i";
gantt.init("gantt_here");
gantt.parse({
    data: [
        {id: 2, text: "Task #1", start_date: "2019-08-01 00:00", duration:5, progress: 1},
        {id: 3, text: "Task #2", start_date: "2019-08-06 00:00", duration:2, progress: 0.5},
        {id: 4, text: "Task #3", start_date: null, duration: null, progress: 0.8, open: true},
        {id: 5, text: "Task #3.1", start_date: "2019-08-09 00:00", duration:2, progress: 0.2},
        {id: 6, text: "Task #3.2", start_date: "2019-08-11 00:00", duration:1, progress: 0}
    ],
});