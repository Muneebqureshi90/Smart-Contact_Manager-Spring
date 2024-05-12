console.log(
    "This is console"
)
$(document).ready(function () {
    const toggleSidebar = () => {
        if ($(".sidebar").is(":visible")) {
            $(".sidebar").css("display", "none");
            $(".content").css("margin-left", "0%");
        } else {
            $(".sidebar").css("display", "block");
            $(".content").css("margin-left", "20%");
        }
    };

    // Add this line to call the function when any element with class sidebarToggle is clicked
    $(".sidebarToggle").click(toggleSidebar);
});

