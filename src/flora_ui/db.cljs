(ns flora-ui.db)

;; https://material-ui-next.com/customization/theme-default/
(def default-theme
  {:direction "ltr"
   :palette {:common {:black "#000"
                      :white "#fff"}
             :type "light"
             :primary {:light "#7986cb"
                       :main "#3f51b5"
                       :dark "#303f9f"
                       :contrast-text "#fff"}

             :secondary {:light "#ff4081"
                         :main "#f50057"
                         :dark "#c51162"
                         :contrast-text "#fff"}

             :error {:light "#e57373"
                     :main "#f44336"
                     :dark "#d32f2f"
                     :contrast-text "#fff"}

             :grey {"50" "#fafafa"
                    "100" "#f5f5f5"
                    "200" "#eeeeee"
                    "300" "#e0e0e0"
                    "400" "#bdbdbd"
                    "500" "#9e9e9e"
                    "600" "#757575"
                    "700" "#616161"
                    "800" "#424242"
                    "900" "#212121"
                    "A100" "#d5d5d5"
                    "A200" "#aaaaaa"
                    "A400" "#303030"
                    "A700" "#616161"}
             :contrast-threshold 3
             :get-contrast-text "TODO"
             :tonal-offset 0.2

             :text {:primary "rgba(0, 0, 0, 0.87)"
                    :secondary "rgba(0, 0, 0, 0.54)"
                    :disabled "rgba(0, 0, 0, 0.38)"
                    :hint "rgba(0, 0, 0, 0.38)"}

             :divider "rgba(0, 0, 0, 0.12)"

             :background {:paper "#fff"
                          :default "#fafafa"}

             :action {:active "rgba(0, 0, 0, 0.54)"
                      :hover "rgba(0, 0, 0, 0.14)"
                      :selected "rgba(0, 0, 0, 0.08)"
                      :disabled "rgba(0, 0, 0, 0.26)"
                      :disabled-background "rgba(0, 0, 0, 0.12)"}}

   :typography {:px->rem "TODO"
                :round "TODO"
                :font-family "\"Roboto\", \"Helvetica\", \"Arial\", sans-serif"
                :font-size 14
                :font-weight-light 300
                :font-weight-regular 400
                :font-weight-medium 500

                :display4 {:font-size "7rem"
                           :font-weight 300
                           :font-family "\"Roboto\", \"Helvetica\", \"Arial\", sans-serif"
                           :letter-spacing "-.04em"
                           :line-height "1.14286em"
                           :margin-left "-.06em"
                           :color "rgba(0, 0, 0, 0.54)"}

                :display3 {:font-size "3.5rem"
                           :font-weight 400
                           :font-family "\"Roboto\", \"Helvetica\", \"Arial\", sans-serif"
                           :letter-spacing "-.02em"
                           :line-height "1.30357em"
                           :margin-left "-.04em"
                           :color "rgba(0, 0, 0, 0.54)"}

                :display2 {:font-size "2.8125rem"
                           :font-weight 400
                           :font-family "\"Roboto\", \"Helvetica\", \"Arial\", sans-serif"
                           :line-height "1.06667em"
                           :margin-left "-.04em"
                           :color "rgba(0, 0, 0, 0.54)"}

                :display1 {:font-size "2.125rem"
                           :font-weight 400
                           :font-family "\"Roboto\", \"Helvetica\", \"Arial\", sans-serif"
                           :line-height "1.20588em"
                           :margin-left "-.04em"
                           :color "rgba(0, 0, 0, 0.54)"}

                :headline {:font-size "1.5rem"
                           :font-weight 400
                           :font-family "\"Roboto\", \"Helvetica\", \"Arial\", sans-serif"
                           :line-height "1.35417em"
                           :color "rgba(0, 0, 0, 0.87)"}

                :title {:font-size "1.3125rem"
                        :font-weight 500
                        :font-family "\"Roboto\", \"Helvetica\", \"Arial\", sans-serif"
                        :line-height "1.16667em"
                        :color "rgba(0, 0, 0, 0.87)"}

                :subheading {:font-size "1rem"
                             :font-weight 400
                             :font-family "\"Roboto\", \"Helvetica\", \"Arial\", sans-serif"
                             :line-height "1.5em"
                             :color "rgba(0, 0, 0, 0.87)"}

                :body2 {:font-size "0.875rem"
                        :font-weight 500
                        :font-family "\"Roboto\", \"Helvetica\", \"Arial\", sans-serif"
                        :line-height "1.71429em"
                        :color "rgba(0, 0, 0, 0.87)"}

                :body1 {:font-size "0.875rem"
                        :font-weight 400
                        :font-family "\"Roboto\", \"Helvetica\", \"Arial\", sans-serif"
                        :line-height "1.46429em"
                        :color "rgba(0, 0, 0, 0.87)"}

                :caption {:font-size "0.75rem"
                          :font-weight 400
                          :font-family "\"Roboto\", \"Helvetica\", \"Arial\", sans-serif"
                          :line-height "1.375em"
                          :color "rgba(0, 0, 0, 0.54)"}

                :button {:font-size "0.875rem"
                         :text-transform "uppercase"
                         :font-weight 500
                         :font-family "\"Roboto\", \"Helvetica\", \"Arial\", sans-serif"}}

   :mixins {:gutters "TODO"
            :toolbar {:min-height 56
                      "@media (min-width:0px) and (orientation: landscape)" {:min-height 48}
                      "@media (min-width:600px)" {:min-height 64}}}

   :breakpoints {:keys ["xs"
                        "sm"
                        "md"
                        "lg"
                        "xl"]

                 :values {:xs 0
                          :sm 600
                          :md 960
                          :lg 1280
                          :xl 1920}

                 :up "TODO"
                 :down "TODO"
                 :between "TODO"
                 :only "TODO"
                 :width "TODO"}

   :shadows {0 "none"
             1 "0px 1px 3px 0px rgba(0, 0, 0, 0.2),0px 1px 1px 0px rgba(0, 0, 0, 0.14),0px 2px 1px -1px rgba(0, 0, 0, 0.12)"
             2 "0px 1px 5px 0px rgba(0, 0, 0, 0.2),0px 2px 2px 0px rgba(0, 0, 0, 0.14),0px 3px 1px -2px rgba(0, 0, 0, 0.12)"
             3 "0px 1px 8px 0px rgba(0, 0, 0, 0.2),0px 3px 4px 0px rgba(0, 0, 0, 0.14),0px 3px 3px -2px rgba(0, 0, 0, 0.12)"
             4 "0px 2px 4px -1px rgba(0, 0, 0, 0.2),0px 4px 5px 0px rgba(0, 0, 0, 0.14),0px 1px 10px 0px rgba(0, 0, 0, 0.12)"
             5 "0px 3px 5px -1px rgba(0, 0, 0, 0.2),0px 5px 8px 0px rgba(0, 0, 0, 0.14),0px 1px 14px 0px rgba(0, 0, 0, 0.12)"
             6 "0px 3px 5px -1px rgba(0, 0, 0, 0.2),0px 6px 10px 0px rgba(0, 0, 0, 0.14),0px 1px 18px 0px rgba(0, 0, 0, 0.12)"
             7 "0px 4px 5px -2px rgba(0, 0, 0, 0.2),0px 7px 10px 1px rgba(0, 0, 0, 0.14),0px 2px 16px 1px rgba(0, 0, 0, 0.12)"
             8 "0px 5px 5px -3px rgba(0, 0, 0, 0.2),0px 8px 10px 1px rgba(0, 0, 0, 0.14),0px 3px 14px 2px rgba(0, 0, 0, 0.12)"
             9 "0px 5px 6px -3px rgba(0, 0, 0, 0.2),0px 9px 12px 1px rgba(0, 0, 0, 0.14),0px 3px 16px 2px rgba(0, 0, 0, 0.12)"
             10 "0px 6px 6px -3px rgba(0, 0, 0, 0.2),0px 10px 14px 1px rgba(0, 0, 0, 0.14),0px 4px 18px 3px rgba(0, 0, 0, 0.12)"
             11 "0px 6px 7px -4px rgba(0, 0, 0, 0.2),0px 11px 15px 1px rgba(0, 0, 0, 0.14),0px 4px 20px 3px rgba(0, 0, 0, 0.12)"
             12 "0px 7px 8px -4px rgba(0, 0, 0, 0.2),0px 12px 17px 2px rgba(0, 0, 0, 0.14),0px 5px 22px 4px rgba(0, 0, 0, 0.12)"
             13 "0px 7px 8px -4px rgba(0, 0, 0, 0.2),0px 13px 19px 2px rgba(0, 0, 0, 0.14),0px 5px 24px 4px rgba(0, 0, 0, 0.12)"
             14 "0px 7px 9px -4px rgba(0, 0, 0, 0.2),0px 14px 21px 2px rgba(0, 0, 0, 0.14),0px 5px 26px 4px rgba(0, 0, 0, 0.12)"
             15 "0px 8px 9px -5px rgba(0, 0, 0, 0.2),0px 15px 22px 2px rgba(0, 0, 0, 0.14),0px 6px 28px 5px rgba(0, 0, 0, 0.12)"
             16 "0px 8px 10px -5px rgba(0, 0, 0, 0.2),0px 16px 24px 2px rgba(0, 0, 0, 0.14),0px 6px 30px 5px rgba(0, 0, 0, 0.12)"
             17 "0px 8px 11px -5px rgba(0, 0, 0, 0.2),0px 17px 26px 2px rgba(0, 0, 0, 0.14),0px 6px 32px 5px rgba(0, 0, 0, 0.12)"
             18 "0px 9px 11px -5px rgba(0, 0, 0, 0.2),0px 18px 28px 2px rgba(0, 0, 0, 0.14),0px 7px 34px 6px rgba(0, 0, 0, 0.12)"
             19 "0px 9px 12px -6px rgba(0, 0, 0, 0.2),0px 19px 29px 2px rgba(0, 0, 0, 0.14),0px 7px 36px 6px rgba(0, 0, 0, 0.12)"
             20 "0px 10px 13px -6px rgba(0, 0, 0, 0.2),0px 20px 31px 3px rgba(0, 0, 0, 0.14),0px 8px 38px 7px rgba(0, 0, 0, 0.12)"
             21 "0px 10px 13px -6px rgba(0, 0, 0, 0.2),0px 21px 33px 3px rgba(0, 0, 0, 0.14),0px 8px 40px 7px rgba(0, 0, 0, 0.12)"
             22 "0px 10px 14px -6px rgba(0, 0, 0, 0.2),0px 22px 35px 3px rgba(0, 0, 0, 0.14),0px 8px 42px 7px rgba(0, 0, 0, 0.12)"
             23 "0px 11px 14px -7px rgba(0, 0, 0, 0.2),0px 23px 36px 3px rgba(0, 0, 0, 0.14),0px 9px 44px 8px rgba(0, 0, 0, 0.12)"
             24 "0px 11px 15px -7px rgba(0, 0, 0, 0.2),0px 24px 38px 3px rgba(0, 0, 0, 0.14),0px 9px 46px 8px rgba(0, 0, 0, 0.12)"}

   :transitions {:easing {:ease-in-out "cubic-bezier(0.4, 0, 0.2, 1)"
                          :ease-out "cubic-bezier(0.0, 0, 0.2, 1)"
                          :ease-in "cubic-bezier(0.4, 0, 1, 1)"
                          :sharp "cubic-bezier(0.4, 0, 0.6, 1)"}

                 :duration {:shortest 150
                            :shorter 200
                            :short 250
                            :standard 300
                            :complex 375
                            :entering-screen 225
                            :leaving-screen 195}
                 :create "TODO"
                 :get-auto-height-duration "TODO"}

   :spacing {:unit 8}
   :z-index {:mobile-stepper 1000
             :app-bar 1100
             :drawer 1200
             :modal 1300
             :snackbar 1400
             :tooltip 1500}})
