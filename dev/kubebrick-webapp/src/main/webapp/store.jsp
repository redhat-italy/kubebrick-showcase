<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>


<html>

<head>
    <style>
        * {
            box-sizing: border-box;
        }

        /* Create three equal columns that floats next to each other */
        .column {
            float: left;
            width: 31.33%;
            padding: 15px;
            margin: 5px;
            height: 600px;
            /*Should be removed. Only for demonstration */
        }

        /* Clear floats after the columns */
        .row:after {
            content: "";
            display: table;
            clear: both;
        }

        #pieceslot {
            font-family: Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        #pieceslot td,
        #pieceslot th {
            border: 1px solid #ddd;
            padding: 8px;
        }

        #pieceslot tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        #pieceslot tr:hover {
            background-color: #ddd;
        }

        #pieceslot th {
            padding-top: 12px;
            padding-bottom: 12px;
            text-align: left;
            background-color: #04AA6D;
            color: white;
        }
    </style>
    <title>
        <% out.println("KubeBrick Store Manager" );%> - Sale!
    </title>
</head>

<body style="margin:0; padding:0">
    <% int resultColumn=0; boolean rowClosed=false; %>
        <div style="border-style: solid; border-width: thin;  background-color: black; width: 100%;">
            <center>
                <h1 style="color:white">
                    <% out.println("KubeBrick Store Manager" );%>
                </h1>
            </center>
        </div>
        <br>

        <table id="pieceslot">
            <tr>
                <th>Piece color</th>
                <th>Quantity</th>
                <th>Creation Timestamp</th>
            </tr>
            <logic:iterate name="piecesLots" id="piecesLots">
                <tr>
                    <td>
                        <bean:write name="piecesLots" property="color" />
                    </td>
                    <td>
                        <bean:write name="piecesLots" property="pieces" />
                    </td>
                    <td>
                        <bean:write name="piecesLots" property="creationtimestamp" />
                    </td>
                </tr>
            </logic:iterate>
        </table>

</body>

</html>