$(document).ready(function () {
    $(".iqdropdown").iqDropdown();
    $("#orderForm").submit(function (event) {
        $(".iqdropdown-menu-option").each(function (i, obj) {
            let id=$(obj).attr('data-id');
            let quantity=$(obj).find(".counter").html();
            $("#inputs").append("<input hidden name=\"ids\" value=\""+id+"\"/>"+
                                "<input hidden name=\"quantities\" value=\""+quantity+"\"/>");
        })
    })
});