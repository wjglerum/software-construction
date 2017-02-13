module UI.Widget.Boolean exposing (view)

import Html exposing (Html, div, label, input)
import Html.Attributes exposing (type_, id, class, checked)
import Html.Events exposing (onCheck)
import UI.FormData as FormData
import UI.Widget.Base exposing (WidgetContext)
import Values exposing (Value(Boolean))


view : WidgetContext msg -> Html msg
view { field, formData, onChange } =
    let
        isChecked =
            FormData.getBoolean field.id formData
                |> Maybe.withDefault False
    in
        div [ class "checkbox" ]
            [ label []
                [ input
                    [ type_ "checkbox"
                    , id field.id
                    , checked isChecked
                    , onCheck (Boolean >> onChange)
                    ]
                    []
                ]
            ]
