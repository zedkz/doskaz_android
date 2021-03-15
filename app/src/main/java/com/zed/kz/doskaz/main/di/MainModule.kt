package com.zed.kz.doskaz.main.di

import com.google.firebase.auth.PhoneAuthProvider
import com.zed.kz.doskaz.entity.AddObject
import com.zed.kz.doskaz.entity.ListItem
import com.zed.kz.doskaz.main.presentation.activity.MainActivityPresenter
import com.zed.kz.doskaz.main.presentation.auth.bonus.BonusPresenter
import com.zed.kz.doskaz.main.presentation.auth.choose_language.ChooseLanguagePresenter
import com.zed.kz.doskaz.main.presentation.auth.sign_in.SignInPresenter
import com.zed.kz.doskaz.main.presentation.auth.sms.SmsPresenter
import com.zed.kz.doskaz.main.presentation.auth.welcome.WelcomePresenter
import com.zed.kz.doskaz.main.presentation.dialog.list.ListDialogPresenter
import com.zed.kz.doskaz.main.presentation.dialog.map.MapDialogPresenter
import com.zed.kz.doskaz.main.presentation.main.about.AboutPresenter
import com.zed.kz.doskaz.main.presentation.main.blog.details.BlogDetailsPresenter
import com.zed.kz.doskaz.main.presentation.main.blog.filter.BlogFilterPresenter
import com.zed.kz.doskaz.main.presentation.main.blog.list.BlogListPresenter
import com.zed.kz.doskaz.main.presentation.main.category.DisabilityCategoryPresenter
import com.zed.kz.doskaz.main.presentation.main.contacts.ContactsPresenter
import com.zed.kz.doskaz.main.presentation.main.filter.FilterPresenter
import com.zed.kz.doskaz.main.presentation.main.search.SearchPresenter
import com.zed.kz.doskaz.main.presentation.main.home.HomePresenter
import com.zed.kz.doskaz.main.presentation.main.instruction.InstructionPresenter
import com.zed.kz.doskaz.main.presentation.main.map.MapPresenter
import com.zed.kz.doskaz.main.presentation.main.objects.complaint.ObjectComplaintPresenter
import com.zed.kz.doskaz.main.presentation.main.objects.description.DescriptionObjectPresenter
import com.zed.kz.doskaz.main.presentation.main.objects.history.HistoryObjectPresenter
import com.zed.kz.doskaz.main.presentation.main.objects.info.category.ObjectInfoCategoryPresenter
import com.zed.kz.doskaz.main.presentation.main.objects.info.details.ObjectInfoDetailsPresenter
import com.zed.kz.doskaz.main.presentation.main.objects.main.MainObjectPresenter
import com.zed.kz.doskaz.main.presentation.main.objects.photo.PhotoObjectPresenter
import com.zed.kz.doskaz.main.presentation.main.objects.review.create.CreateObjectReviewPresenter
import com.zed.kz.doskaz.main.presentation.main.objects.review.main.ReviewObjectPresenter
import com.zed.kz.doskaz.main.presentation.main.objects.video.VideoObjectPresenter
import com.zed.kz.doskaz.main.presentation.main.settings.SettingsPresenter
import com.zed.kz.doskaz.main.presentation.objects.add.hard_medium.common.ObjectAddCommonPresenter
import com.zed.kz.doskaz.main.presentation.objects.add.hard_medium.dynamic.ObjectAddDynamicPresenter
import com.zed.kz.doskaz.main.presentation.objects.add.hard_medium.hard.ObjectAddHardCategoryPresenter
import com.zed.kz.doskaz.main.presentation.objects.add.hard_medium.category.ObjectAddCategoryPresenter
import com.zed.kz.doskaz.main.presentation.objects.add.simple.AddSimpleObjectPresenter
import com.zed.kz.doskaz.main.presentation.profile.edit.EditProfilePresenter
import com.zed.kz.doskaz.main.presentation.profile.main.show.ShowProfilePresenter
import com.zed.kz.doskaz.main.presentation.profile.my.award.MyAwardPresenter
import com.zed.kz.doskaz.main.presentation.profile.my.comment.MyCommentPresenter
import com.zed.kz.doskaz.main.presentation.profile.my.complaint.MyComplaintPresenter
import com.zed.kz.doskaz.main.presentation.profile.my.objects.MyObjectsPresenter
import com.zed.kz.doskaz.main.presentation.profile.my.task.MyTaskPresenter
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mainModule = module {

    scope(named(MainScope.MAIN_ACTIVITY_SCOPE)){
        scoped { MainActivityPresenter(get()) }
    }

    scope(named(MainScope.CHOOSE_LANGUAGE_SCOPE)){
        scoped { ChooseLanguagePresenter() }
    }

    scope(named(MainScope.WELCOME_SCOPE)){
        scoped { WelcomePresenter(get()) }
    }

    scope(named(MainScope.SIGN_IN_SCOPE)){
        scoped { SignInPresenter(get(), get()) }
    }

    scope(named(MainScope.SMS_SCOPE)){
        scoped { (
                     phone: String,
                     storeVerificationId: String,
                     resentToken: PhoneAuthProvider.ForceResendingToken
                 ) -> SmsPresenter(phone, storeVerificationId, resentToken, get(), get()) }
    }

    scope(named(MainScope.BONUS_SCOPE)){
        scoped { BonusPresenter() }
    }

    scope(named(MainScope.SHOW_PROFILE_SCOPE)){
        scoped { (type: String?) -> ShowProfilePresenter(type, get()) }
    }

    scope(named(MainScope.EDIT_PROFILE_SCOPE)){
        scoped { EditProfilePresenter(get(), get()) }
    }

    scope(named(MainScope.MY_TASK_SCOPE)){
        scoped { MyTaskPresenter(get()) }
    }

    scope(named(MainScope.MY_OBJECTS_SCOPE)){
        scoped { MyObjectsPresenter(get()) }
    }

    scope(named(MainScope.MY_COMMENTS_SCOPE)){
        scoped { MyCommentPresenter(get()) }
    }

    scope(named(MainScope.MY_COMPLAINT_SCOPE)){
        scoped { MyComplaintPresenter(get()) }
    }

    scope(named(MainScope.MY_AWARD_SCOPE)){
        scoped { MyAwardPresenter(get()) }
    }

    scope(named(MainScope.ADD_SIMPLE_OBJECT_SCOPE)){
        scoped { AddSimpleObjectPresenter(get(), get(), get()) }
    }

    scope(named(MainScope.LIST_DIALOG_SCOPE)){
        scoped { (dataList: List<ListItem>) -> ListDialogPresenter(dataList) }
    }

    scope(named(MainScope.HOME_SCOPE)){
        scoped { HomePresenter(get()) }
    }

    scope(named(MainScope.MAP_SCOPE)){
        scoped { MapPresenter(get(), get()) }
    }

    scope(named(MainScope.SEARCH_SCOPE)){
        scoped { (cityId: Int) -> SearchPresenter(cityId, get()) }
    }

    scope(named(MainScope.FILTER_SCOPE)){
        scoped { FilterPresenter(get()) }
    }

    scope(named(MainScope.MAIN_OBJECT_SCOPE)){
        scoped { (objectId: Int) -> MainObjectPresenter(objectId, get(), get()) }
    }

    scope(named(MainScope.DESCRIPTION_OBJECT_SCOPE)){
        scoped { DescriptionObjectPresenter(get(), get()) }
    }

    scope(named(MainScope.PHOTO_OBJECT_SCOPE)){
        scoped { PhotoObjectPresenter() }
    }

    scope(named(MainScope.REVIEW_OBJECT_SCOPE)){
        scoped { ReviewObjectPresenter() }
    }

    scope(named(MainScope.VIDEO_OBJECT_SCOPE)){
        scoped { VideoObjectPresenter() }
    }

    scope(named(MainScope.HISTORY_OBJECT_SCOPE)){
        scoped { HistoryObjectPresenter() }
    }

    scope(named(MainScope.CREATE_OBJECT_REVIEW_SCOPE)){
        scoped { (blogId: Int, parentId: String) -> CreateObjectReviewPresenter(blogId, parentId, get(), get(), get()) }
    }

    scope(named(MainScope.ABOUT_SCOPE)){
        scoped { AboutPresenter() }
    }

    scope(named(MainScope.INSTRUCTION_SCOPE)){
        scoped { InstructionPresenter() }
    }

    scope(named(MainScope.CONTACTS_SCOPE)){
        scoped { ContactsPresenter(get(), get(), get()) }
    }

    scope(named(MainScope.BLOG_LIST_SCOPE)){
        scoped { BlogListPresenter(get()) }
    }

    scope(named(MainScope.BLOG_DETAILS_SCOPE)){
        scoped { (blogId: Int) -> BlogDetailsPresenter(blogId, get()) }
    }

    scope(named(MainScope.BLOG_FILTER_SCOPE)){
        scoped { BlogFilterPresenter(get()) }
    }

    scope(named(MainScope.MAP_DIALOG_SCOPE)){
        scoped { MapDialogPresenter(get()) }
    }

    scope(named(MainScope.DISABILITY_CATEGORY_SCOPE)){
        scoped { (isFromSettings: Boolean) -> DisabilityCategoryPresenter(isFromSettings, get()) }
    }

    scope(named(MainScope.OBJECT_COMPLAINT_SCOPE)){
        scoped { (objectId: Int?) -> ObjectComplaintPresenter(objectId, get(), get(), get()) }
    }

    scope(named(MainScope.OBJECT_ADD_HARD_CATEGORY_SCOPE)){
        scoped { ObjectAddHardCategoryPresenter() }
    }

    scope(named(MainScope.OBJECT_ADD_MEDIUM_CATEGORY_SCOPE)){
        scoped { (type: String) -> ObjectAddCategoryPresenter(type, get(), get(), get()) }
    }

    scope(named(MainScope.OBJECT_ADD_COMMON_SCOPE)){
        scoped { ObjectAddCommonPresenter(get(), get(), get()) }
    }

    scope(named(MainScope.OBJECT_ADD_DYNAMIC_SCOPE)){
        scoped { (type: String, dataList: List<AddObject>) -> ObjectAddDynamicPresenter(type, dataList, get(), get(), get()) }
    }

    scope(named(MainScope.OBJECT_INFO_DETAILS_SCOPE)){
        scoped { (dataList: List<AddObject>) -> ObjectInfoDetailsPresenter(dataList) }
    }

    scope(named(MainScope.OBJECT_INFO_CATEGORY_SCOPE)){
        scoped { ObjectInfoCategoryPresenter(get(), get(), get()) }
    }

    scope(named(MainScope.SETTINGS_SCOPE)){
        scoped { SettingsPresenter(get(), get()) }
    }

}

object MainScope{

    const val MAIN_ACTIVITY_SCOPE = "MainActivityScope"
    const val CHOOSE_LANGUAGE_SCOPE = "ChooseLanguageScope"
    const val WELCOME_SCOPE = "WelcomeScope"
    const val SIGN_IN_SCOPE = "SignInScope"
    const val SMS_SCOPE = "SmsScope"
    const val BONUS_SCOPE = "BonusScope"
    const val SHOW_PROFILE_SCOPE = "ShowProfileScope"
    const val EDIT_PROFILE_SCOPE = "EditProfileScope"
    const val MY_TASK_SCOPE = "MyTaskScope"
    const val MY_OBJECTS_SCOPE = "MyObjectsScope"
    const val MY_COMMENTS_SCOPE = "MyCommentsScope"
    const val MY_COMPLAINT_SCOPE = "MyComplaintScope"
    const val MY_AWARD_SCOPE = "MyAwardScope"
    const val ADD_SIMPLE_OBJECT_SCOPE = "AddSimpleObjectScope"
    const val LIST_DIALOG_SCOPE = "ListDialogScope"
    const val HOME_SCOPE = "HomeScope"
    const val MAP_SCOPE = "MapScope"
    const val SEARCH_SCOPE = "SearchScope"
    const val FILTER_SCOPE = "FilterScope"
    const val MAIN_OBJECT_SCOPE = "MainObjectScope"
    const val DESCRIPTION_OBJECT_SCOPE = "DescriptionObjectScope"
    const val PHOTO_OBJECT_SCOPE = "PhotoObjectScope"
    const val REVIEW_OBJECT_SCOPE = "ReviewObjectScope"
    const val VIDEO_OBJECT_SCOPE = "VideoObjectScope"
    const val HISTORY_OBJECT_SCOPE = "HistoryObjectScope"
    const val CREATE_OBJECT_REVIEW_SCOPE = "CreateObjectReviewScope"
    const val ABOUT_SCOPE = "AboutScope"
    const val INSTRUCTION_SCOPE = "InstructionScope"
    const val CONTACTS_SCOPE = "ContactsScope"
    const val BLOG_LIST_SCOPE = "BlogListScope"
    const val BLOG_DETAILS_SCOPE = "BlogDetailsScope"
    const val BLOG_FILTER_SCOPE = "BlogFilterScope"
    const val MAP_DIALOG_SCOPE = "MapDialogScope"
    const val DISABILITY_CATEGORY_SCOPE = "DisabilityCategoryScope"
    const val OBJECT_COMPLAINT_SCOPE = "ObjectComplaintScope"
    const val OBJECT_ADD_HARD_CATEGORY_SCOPE = "ObjectAddHardCategoryScope"
    const val OBJECT_ADD_MEDIUM_CATEGORY_SCOPE = "ObjectAddMediumCategoryScope"
    const val OBJECT_ADD_COMMON_SCOPE = "ObjectAddCommonScope"
    const val OBJECT_ADD_DYNAMIC_SCOPE = "ObjectAddDynamicScope"
    const val OBJECT_INFO_DETAILS_SCOPE = "ObjectInfoDetailsScope"
    const val OBJECT_INFO_CATEGORY_SCOPE = "ObjectInfoCategoryScope"
    const val SETTINGS_SCOPE = "SettingsScope"

}