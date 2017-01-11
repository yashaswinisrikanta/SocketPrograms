module Handler.Home where

import Import
import Yesod.Form.Bootstrap3 (BootstrapFormLayout (..), renderBootstrap3)
import Text.Julius (RawJS (..))
import Yesod.Auth
import Yesod.Auth.OAuth2.Github

-- Define our data that will be used for creating the form.
data FileForm = FileForm
    { fileInfo :: FileInfo
    , fileDescription :: Text
    }
data JsonForm = JsonForm
    {
      sessValue ::Maybe Text
    , username :: Text
    , reponame :: Text
    , urlname :: Text
    , tokenname :: Maybe Text
    , repoType :: Maybe Text
    , queryType :: Maybe Text
    , selectname :: Maybe Text

}
-- This is a handler function for the GET request method on the HomeR
-- resource pattern. All of your resource patterns are defined in
-- config/routes
--
-- The majority of the code you will write in Yesod lives in these handler
-- functions. You can spread them across multiple files if you are so
-- inclined, or create a single monolithic file.
getHomeR :: Handler Html
getHomeR = do
    (formWidget, formEnctype) <- generateFormPost sampleForm
    let submission = Nothing :: Maybe FileForm
        handlerName = "getHomeR" :: Text
    defaultLayout $ do
        sess <- getSession
        msessValue <- lookupSession "AccessToken"
        let sessValue = show msessValue
        let (sessValue, username, reponame, urlname, tokenname, repoType, queryType, selectname) = searchIds
        aDomId <- newIdent
        setTitle "Welcome To Yesod!"
        $(widgetFile "search")

postHomeR :: Handler Html
postHomeR = do
    ((result, formWidget), formEnctype) <- runFormPost sampleForm
    let handlerName = "postHomeR" :: Text
        submission = case result of
            FormSuccess res -> Just res
            _ -> Nothing

    defaultLayout $ do
        let (sessValue, username, reponame, urlname, tokenname, repoType, queryType, selectname) = searchIds
        aDomId <- newIdent
        setTitle "Welcome To Yesod!"
        $(widgetFile "search")

sampleForm :: Form FileForm
sampleForm = renderBootstrap3 BootstrapBasicForm $ FileForm
    <$> fileAFormReq "Choose a file"
    <*> areq textField textSettings Nothing
    -- Add attributes like the placeholder and CSS classes.
    where textSettings = FieldSettings
            { fsLabel = "What's on the file?"
            , fsTooltip = Nothing
            , fsId = Nothing
            , fsName = Nothing
            , fsAttrs =
                [ ("class", "form-control")
                , ("placeholder", "File description")
                ]
            }

searchIds :: (Text, Text, Text, Text, Text, Text, Text, Text)
searchIds = ("js-sessValue", "js-username", "js-reponame", "js-urlname","js-tokenname", "js-repoType", "js-queryType", "js-selectname")

commentIds :: (Text, Text, Text)
commentIds = ("js-commentForm", "js-createCommentTextarea", "js-commentList")
