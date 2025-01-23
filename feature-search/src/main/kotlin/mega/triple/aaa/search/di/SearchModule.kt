package mega.triple.aaa.search.di

import mega.triple.aaa.search.SearchViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureSearchModule = module {
    viewModelOf(::SearchViewModel)
}
