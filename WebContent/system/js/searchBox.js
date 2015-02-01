$("#searchBox").keydown(function(e){
        var rtVal = false;
        $("#search__content .active").removeClass("active");
        var index = $(this).attr("data-index") || 0,
            len = $(this).attr("data-len") || 0;

        console.log(e.keyCode);
        if(e.keyCode == 38) {
            index = --index > 0 ? index : 0;
            $("#search__content a:eq("+index+")").addClass("active");
        }else if(e.keyCode == 40) {
            index = ++index < len ? index : len-1;
            $("#search__content a:eq("+index+")").addClass("active");
        }else if(e.keyCode == 40) {
            index = ++index < len ? index : len-1;
            $("#search__content a:eq("+index+")").addClass("active");
        }else{
            reVal = true;
        }

        var value = $("#search__content .active").text();

        if(value) {
            $(this).val(value);
        }
        $(this).attr("data-index", index);
        $(this).attr("data-len", len);

        clearTimeout(handle);
        handle = setTimeout(function(){
            $.get("./js/searchTest.json", "", function(e){
                var data = e,
                    len = e.length;

                if(len && len > 0) {
                    $(".search").show();

                    $("#searchBox").attr("data-len", len).attr("data-index", 0);

                    $("#search__content").html("");
                    for(var i = 0; i<data.length; i++) {
                        $("#search__content").append("<a href='javascript:void(0)' data-index='"+i+"'>" + data[i] + "</a>");
                    }
                }

            })
        }, 400);
        return reVal;
    });

    $(".search__content").on("mouseover", "a", function(){
        $(".search__content .active").removeClass("active");
        $(this).addClass("active");
        $("#searchBox").val($(this).text()).attr("data-index", $(this).attr("data-index"));

    });

    $(".search__content").on("click", "a", function(){
        $(".search").hide();
        $("#searchBox").val($(this).text());
    });