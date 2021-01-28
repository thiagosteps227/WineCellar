# WineCellar

This program is an API created with JAX-RS for academic purposes... This API consumes data from a MySQL database.
This API is very easy to use. The user must run in Apache server and the api can be accessed in the end point localserver:8080/rest/wines.

Observe in the ConnectionHelper.java:

Be sure if your MySQL has password or not. In case it doesn't, erase the "&password=admin" part of the url.
