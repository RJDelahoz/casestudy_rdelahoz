$(window).load(function () {
	$dropdown = $("#contextMenu");
	$(".actionButton").click(function () {
		//get row ID
		var id = $(this).closest("tr").children().first().html();
		//move dropdown menu
		$(this).after($dropdown);
		//update links
		$dropdown.find(".payLink").attr("href", "/transaction/pay?id=" + id);
		$dropdown.find(".delLink").attr("href", "/transaction/delete?id=" + id);
		//show dropdown
		$(this).dropdown();
	});
});
