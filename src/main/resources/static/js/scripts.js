$(document).ready(function() {
    $('.coach, .player, .team').css('cursor', 'pointer').click(function(e) {
        if (!$(e.target).is(':checkbox')) {
            window.location = $(this).data('href');
        }
    });

    $('.renovate').click(function() {
        const selectedIds = [];
        $('input[name="selectedIds"]:checked').each(function() {
            selectedIds.push($(this).val());
        });


        if (selectedIds.length > 0 ) {
            $.post('/player/renovate', { selectedIds: selectedIds }, function() {
                window.location.href = '/player/list';
            });
        }
    });
});



