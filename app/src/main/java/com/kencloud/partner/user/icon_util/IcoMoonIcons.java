package com.kencloud.partner.user.icon_util;


import com.joanzapata.iconify.Icon;

public enum IcoMoonIcons implements Icon {

    ic_like('\ue917'),
    ic_representative('\ue901'),
    ic_achievement('\ue902'),
    ic_events('\ue903'),
    ic_settings('\ue904'),
    ic_manifesto('\ue905'),
    ic_donation('\ue906'),
    ic_social('\ue907'),
    ic_feedback('\ue908'),
    ic_discussion('\ue909'),
    ic_voter_id('\ue90a'),
    ic_announcement('\ue90b'),
    ic_news('\ue90c'),
    ic_registration('\ue90d'),
    ic_gallery('\ue90e'),
    ic_close('\ue936'),
    ic_sandesh('\ue90f'),
    ic_blog('\ue910'),
    ic_party_member('\ue900'),
    ic_search('\ue911'),
    ic_notification('\ue912'),
    ic_regional_development('\ue913'),
    ic_clean_governance('\ue914'),
    ic_community_development('\ue915'),
    ic_health_education('\ue916'),
    ic_women_development('\ue919'),
    ic_agriculture('\ue91a'),
    ic_logout('\ue937'),
    ic_infrastructure('\ue91b'),
    ic_ending_quote('\ue91c'),
    ic_starting_quote('\ue91d'),
    ic_security('\ue91e'),
    ic_arrow('\ue922'),
    ic_facebook_tab_icon('\ue91f'),
    ic_twitter_tab_icon('\ue920'),
    ic_user('\ue935'),
    ic_qualification('\ue921'),
    ic_email('\ue923'),
    ic_pancard('\ue924'),
    ic_calender('\ue925'),
    ic_location('\ue926'),
    ic_prev('\ue929'),
    ic_check('\ue92a'),
    ic_rupee('\ue92b'),
    ic_mobile_no('\ue928'),
    ic_edit('\ue92c'),
    ic_chat('\ue92d'),
    ic_password('\ue92e'),
    ic_more('\ue92f'),
    ic_play('\ue930'),
    ic_pause('\ue931'),
    ic_send('\ue932'),
    ic_noconnection('\ue933'),
    ic_edit_pic('\ue934'),
    ic_default_avatar('\ue935'),
    ic_success('\ue938'),
    ic_home('\ue938'),
    ic_youtube('\ue939'),
    ic_achievements('\ue93a'),
    ic_events_dashboard('\ue93b'),
    ic_forum('\ue93c'),
    ic_google_plus('\ue93d'),
    ic_gallery_dashboard('\ue93e'),
    ic_get_involved('\ue93f'),
    ic_linkedin('\ue940'),
    ic_manifesto_dashboard('\ue941'),
    ic_representative_dashboard('\ue942'),
    ic_sandesh_dashboard('\ue943'),
    ic_camera('\ue945'),
    ic_share('\ue918');

    char character;

    IcoMoonIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}