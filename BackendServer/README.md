# Backend API for our Bulletin Board app

Supported paths:

GET:
    users:
        /users/all/
        /users/id=__id__
        /users/name=__name__
        /users/clear/    This one is just for development/testing. It'll be removed later.

    posts:
        /posts/all/
        /posts/all/limit=__limit__
        /posts/user_id=__user_id__
        /posts/id=__id__
        /posts/ids=__id1__,__id2__,...,__id_n__

POST:
    TODO