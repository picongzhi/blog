"use strict";

$(function () {
    var _pageSize;

    function getBlogsByName(pageIndex, pageSize) {
        $.ajax({
            url: "/u/" + username + "/blogs",
            contentType: 'application/json',
            data: {
                "async": true,
                "pageIndex": pageIndex,
                "pageSize": pageSize,
                "catalogId": catalogId,
                "keyword": $("#keyword").val()
            },
            success: function (data) {
                $("#mainContainer").html(data);
            },
            error: function () {
                toastr.error("error!");
            }
        });
    }

    $.tbpage("#mainContainer", function (pageIndex, pageSize) {
        getBlogsByName(pageIndex, pageSize);
        _pageSize = pageSize;
    });

    $("#searchBlogs").click(function () {
        getBlogsByName(0, _pageSize);
    });

    $(".nav-item .nav-link").click(function () {
        var url = $(this).attr("url");

        $(".nav-item .nav-link").removeClass("active");
        $(this).addClass("active");

        $.ajax({
            url: url + '&async=true',
            success: function (data) {
                $("#mainContainer").html(data);
            },
            error: function () {
                toastr.error("error!");
            }
        });

        $("#keyword").val('');
    });

    function getCatalogs(username) {
        $.ajax({
            url: '/catalogs',
            type: 'GET',
            data: {'username': username},
            success: function (data) {
                $('#catalogMain').html(data);
            },
            error: function () {
                toastr.error('error!');
            }
        })
    }

    $('.blog-content-container').on('click', '.blog-add-catalog', function () {
        $.ajax({
            url: '/catalogs/edit',
            type: 'GET',
            success: function (data) {
                $('#catalogFormContainer').html(data);
            },
            error: function () {
                toastr.error('error!');
            }
        });
    });

    $('.blog-content-container').on('click', '.blog-edit-catalog', function () {
        $.ajax({
            url: '/catalogs/edit/' + $(this).attr('catalogId'),
            type: 'GET',
            success: function (data) {
                $('#catalogFormContainer').html(data);
            },
            error: function () {
                toastr.error('error!');
            }
        });
    });

    $('#submitEditCatalog').click(function () {
        var csrfToken = $("meta[name='_csrf']").attr('content');
        var csrfHeader = $("meta[name='_csrf_header']").attr('content');

        $.ajax({
            url: '/catalogs',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({
                'username': username,
                'catalog': {
                    'id': $('#catalogId').val(),
                    'name': $('#catalogName').val()
                }
            }),
            beforeSend: function (request) {
                request.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                if (data.success) {
                    toastr.info(data.message);
                    getCatalogs(username);
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error('error!');
            }
        });
    });

    $('.blog-content-container').on('click', '.blog-delete-catalog', function () {
        var csrfToken = $("meta[name='_csrf']").attr('content');
        var csrfHeader = $("meta[name='_csrf_header']").attr('content');

        $.ajax({
            url: '/catalogs/' + $(this).attr('catalogId') + '?username=' + username,
            type: 'DELETE',
            beforeSend: function (request) {
                request.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                if (data.success) {
                    toastr.info(data.message);
                    getCatalogs(username);
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error('error!');
            }
        });
    });

    $('.blog-content-container').on('click', '.blog-query-by-catalog', function () {
        catalogId = $(this).attr('catalogId');
        getBlogsByName(0, _pageSize);
    });

    getCatalogs(username)
});