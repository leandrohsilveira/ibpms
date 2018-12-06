<app>

    <app-layout>
        <app-routes />
        <app-message />
    </app-layout>

    <style>

        fieldset .buttons {
            display: flex;
            flex-direction: row-reverse;
        }

        .button.button-danger {
            background-color: red;
            border-color: red;
        }

        .button.button-danger:hover {
            background-color: #606C75;
            border-color: #606C75;
        }

        .button.button-danger.button-outline {
            background-color: transparent;
            color: red;
        }

        .button.button-danger.button-outline:hover {
            color: #606C75;
        }

        .button.button-danger.button-clear {
            background-color: transparent;
            border-color: transparent;
            color: red;
        }

        .button.button-danger.button-clear:hover {
            color: #606C75;
        }

    </style>

</app>