<%@ page import="services.DBStoreService" %>
<%@ page import="java.util.List" %>
<%@ page import="models.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.min.js" ></script>
    <% Announcement announcement = (Announcement) request.getSession().getAttribute("announcment");
        Boolean owner = (Boolean) request.getSession().getAttribute("owner");
        if (announcement != null){ %>
    <title> Изменить объявление</title>
    <% } else { %>
    <title>Добавить объявление</title>
    <% } %>
    <script src="<%=request.getContextPath()%>/script.js"></script>
    <script>
        $(document).ready(function () {
            let sbrands = {};
            let smodels = [];
            <% for (CarBrand brand : DBStoreService.getStoreService().getCarBrands()) {%>
            smodels = [];
            <% for (CarModel model : brand.getModels()) {%>
            smodels.push({
                id: "<%=model.getId()%>",
                name: "<%=model.getName()%>"
            });
            <% } %>
            sbrands["<%=brand.getId()%>"] = smodels;
            <% } %>
            $("#carBrandSelect").change(function () {
                var val = $(this).val();
                let smodel = sbrands[val];
                if (smodel.length > 0) {
                    let s = '';
                    smodel.forEach(el => {
                        s += '<option value=' + el.id + '>' + el.name + '</option>';
                    });
                    $("#carModelSelect").html(s);
                }
            });
        });
    </script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <% if (announcement != null){ %>
                <label  id="id" hidden><%=announcement.getId()%></label>
                <% }%>
                Основные

            </div>
            <div class="card-body">
                <div class="form-group">
                    <label>Название</label>
                    <% if (announcement != null){ %>
                    <input type="text" class="form-control" id="title"  value="<%=announcement.getTitle()%>"required>
                    <% } else { %>
                    <input type="text" class="form-control" id="title" required>
                    <% } %>
                </div>
                <div class="form-group">
                    <label>Описание</label>
                    <% if (announcement != null){ %>
                    <p><textarea id="description" rows="10" cols="45" name="description"><%=announcement.getDescription()%></textarea></p>
                    <% } else { %>
                    <p><textarea id="description" rows="10" cols="45" name="description"></textarea></p>
                    <% } %>
                </div>
                <div class="form-group">
                    <label>Цена</label>
                    <% if (announcement != null){ %>
                    <input type="number" class="form-control" id="price" value="<%=announcement.getPrice()%>"required>
                    <% } else { %>
                    <input type="number" class="form-control" id="price" required>
                    <% } %>
                </div>
            </div>
        </div>
        <div class="card" style="width: 100%">
            <div class="card-header">
                Фотографии
            </div>
            <div class="card-body">
                <table class="table">
                    <thead>
                    <tr id="imgPrevie">
                    </tr>
                    </thead>
                    <tbody id="images">
                    <% if(announcement != null) {
                        for(CarPhoto photo : announcement.getPhotos()) { %>
                    <td><img src="http://localhost:8686/cars_sales_war/upload.do?id=<%=photo.getId()%>&name=<%=photo.getName()%>" width="100px" height="100px"/></td>
                    <% }} %>
                    </tbody>
                </table>
                <% if (announcement == null || owner) {%>
                <div class="checkbox">
                    <input type="file" id="file" multiple>
                </div>
                <button id="upload" onclick="uploadPhotos()"  type="submit" class="btn btn-default">Submit</button>
                <% } %>
            </div>
        </div>
        <div class="card" style="width: 100%">
            <div class="card-header">
                Детали
            </div>
            <div class="card-body">
                <div class="form-group">
                    <p>Марка:</p>
                    <p><select id="carBrandSelect">
                        <% List<CarBrand> brands = DBStoreService.getStoreService().getCarBrands();
                            if(brands != null && brands.size() > 0) {
                                for(CarBrand brand: brands) {
                                    if(announcement != null && announcement.getBrand().getId().equals(brand.getId())) {%>
                        <option value="<%= brand.getId()%>" selected><%= brand.getName()%></option>
                        <% } else { %>
                        <option value="<%= brand.getId()%>"><%= brand.getName()%></option>
                        <% } } }%>
                    </select></p>
                    <p>Модель:</p>
                    <p><select id="carModelSelect">
                        <% List<CarModel> models = brands.get(0).getModels();
                            if(models != null && models.size() > 0) {
                                for(CarModel model: models) {
                                    if(announcement != null && announcement.getModel().getId().equals(model.getId())) {%>
                        <option value="<%= model.getId()%>" selected><%= model.getName()%></option>
                        <% } else { %>
                        <option value="<%= model.getId()%>"><%= model.getName()%></option>
                        <% }}} %>
                    </select></p>
                    <p>Тип двигателя:</p>
                    <p><select id="carEngineSelect">
                        <% List<CarEngine> engines = DBStoreService.getStoreService().getCarEngines();
                            if(engines != null && engines.size() > 0) {
                                for(CarEngine engine: engines) {
                                    if(announcement != null && announcement.getCarEngine().getId().equals(engine.getId())) {%>
                        <option value="<%= engine.getId()%>" selected><%= engine.getName()%></option>
                        <% } else { %>
                        <option value="<%= engine.getId()%>"><%= engine.getName()%></option>
                        <% }}} %>
                    </select></p>
                    <p>Тип кузова:</p>
                    <p><select id="carBodySelect">
                        <% List<CarBody> bodies = DBStoreService.getStoreService().getCarBodies();
                            if(bodies != null && bodies.size() > 0) {
                                for(CarBody body: bodies) {
                                    if(announcement != null && announcement.getCarBody().getId().equals(body.getId())) {%>
                        <option value="<%= body.getId()%>" selected><%= body.getName()%></option>
                        <% } else { %>
                        <option value="<%= body.getId()%>"><%= body.getName()%></option>
                        <% }}} %>
                    </select></p>
                    <p>Коробка передач:</p>
                    <p><select id="carTypeSelect">
                        <% List<CarType> types = DBStoreService.getStoreService().getCarTypes();
                            if(types != null && types.size() > 0) {
                                for(CarType type: types) {
                                    if(announcement != null && announcement.getType().getId().equals(type.getId())) {%>
                        <option value="<%= type.getId()%>" selected><%= type.getName()%></option>
                        <% } else { %>
                        <option value="<%= type.getId()%>"><%= type.getName()%></option>
                        <% }}} %>
                    </select></p>

                    <% if(announcement != null && owner) { %>
                    <p>Статус:</p>
                    <p><select id="announctmentStatus">
                        <% if(announcement.getStatus().equals(0)) { %>
                        <option value="0" selected>Открыто</option>
                        <option value="1">Закрыто</option>
                        <% } else { %>
                        <option value="0">Открыто</option>
                        <option value="1" selected>Закрыто</option>
                        <% } %>
                    </select></p>
                    <% } %>
                </div>
            </div>
        </div>
        <div class="card" style="width: 100%">
            <div class="card-body">
                <div class="form-group">
                    <label>Контакт</label>
                    <% if (announcement != null){ %>
                    <input type="text" class="form-control" id="contact"  value="<%=announcement.getContact()%>"required>
                    <% } else { %>
                    <input type="text" class="form-control" id="contact" required>
                    <% } %>
                </div>
            </div>
        </div>
        <% if(announcement != null) {
            if (owner) { %>
        <button id="add" onclick="changeAnnouncment()" type="submit" class="btn btn-primary">Изменить</button>
        <% } } else { %>
        <button id="add" onclick="createAnnouncment()" type="submit" class="btn btn-primary">Добавить</button>
        <% } %>
    </div>
</div>
</body>
</html>
