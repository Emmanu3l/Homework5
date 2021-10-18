package at.technikum_wien.polzert.news.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.room.PrimaryKey
import at.technikum_wien.polzert.news.R
import at.technikum_wien.polzert.news.viewmodels.NewsListViewModel
import kotlinx.datetime.Instant

@Composable
fun MainScreen(navController: NavController, viewModel : NewsListViewModel) {
    val newsItems by viewModel.newsItems.observeAsState()

    val identifier by viewModel.identifier.observeAsState()
    val title by viewModel.title.observeAsState()
    val link by viewModel.link.observeAsState()
    val description by viewModel.description.observeAsState()
    val imageUrl by viewModel.imageUrl.observeAsState()
    val author by viewModel.author.observeAsState()
    val publicationDate by viewModel.publicationDate.observeAsState()
    val keywords by viewModel.keywords.observeAsState()


    val error by viewModel.error.observeAsState()
    var expanded by remember { mutableStateOf(false) }


    Scaffold(
        topBar = { TopAppBar(
            title = {
                Text(text = stringResource(R.string.app_title))
            },
            actions = {
                IconButton(onClick = {
                    expanded = true
                }) {
                    Icon(
                        Icons.Filled.MoreVert,
                        contentDescription = stringResource(R.string.menu)
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                viewModel.reload()
                            },
                        ) {
                            Text(stringResource(R.string.reload))
                        }
                        DropdownMenuItem(
                            onClick = {
                                navController.navigate(Screen.SettingsScreen.route)
                            },
                        ) {
                            Text(stringResource(R.string.settings))
                        }
                    }
                }
            }
        )
    },
    content =  {
        Column {
            if (error == true)
                //TODO
                Text(text = stringResource(R.string.error_message))
            LazyColumn(Modifier.fillMaxWidth()) {
                itemsIndexed(newsItems ?: listOf()) { index, newsItem ->
                    if (index == 0)
                        NewsItemFirstRow(
                            navController = navController,
                            index = index,
                            newsItem = newsItem,
                            viewModel = viewModel
                        )
                    else
                        NewsItemRow(
                            navController = navController,
                            index = index,
                            newsItem = newsItem,
                            viewModel = viewModel
                        )
                }
            }
        }
    })
}
