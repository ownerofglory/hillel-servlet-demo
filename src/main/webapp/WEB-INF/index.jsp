<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    <%@ page import="ua.hillel.model.TodoList" %>
    <%@ page import="ua.hillel.model.Todo" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
    <style>
        .form {
            margin-top: 20px;
        }

        .hidden-input {
            display: none
        }
    </style>
    <title>Document</title>
</head>
<body>

    <div class="container">
        <div class="row">

            <div class="col-4">

            <% TodoList[] listData = (TodoList[]) request.getAttribute("list-data"); %>

                <form class="form row g-3" method="post" action="<%= request.getContextPath() %>/app/list">
                  <div class="col-auto">
                    <input type="text" name="list" class="form-control" id="inputList" placeholder="Enter list">
                  </div>
                  <div class="col-auto">
                    <button type="submit" class="btn btn-primary mb-3">Add</button>
                  </div>
                </form>


                <div class="list-group">
                 <% for (TodoList list: listData ) { %>
                 <form action="app" method="get">
                   <input class="hidden-input" name="activeListId" value="<%= list.getId() %>" />
                  <button type="submit" class="list-group-item list-group-item-action">
                    <%= list.getName() %>
                  </button>
                 </form>
                <% } %>
                </div>

            </div>

            <div class="col-8">
            <% int activeListId = (int) request.getAttribute("active-list-id"); %>
                <form class="form row g-3" method="post" action="<%= request.getContextPath() %>/app/list/<%= activeListId %>">
                   <div class="col-auto">
                       <input type="text" name="todo" class="form-control" id="inputTodo" placeholder="Enter todo title">
                   </div>
                   <div class="col-auto">
                       <button type="submit" class="btn btn-primary mb-3">Add</button>
                    </div>
                </form>

                <% Todo[] todoData = (Todo[]) request.getAttribute("todo-data"); %>
                <div class="list-group">
                <% for (Todo todo: todoData) { %>
                  <button type="button" class="list-group-item list-group-item-action">
                    <input class="form-check-input" checked="<%= todo.getStatus() %>" type="checkbox" />
                    <%= todo.getTitle() %>
                   </button>
                <% } %>
                </div>
            </div>

        </div>

        <div class="row">



        </div>
    </div>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>

</body>
</html>