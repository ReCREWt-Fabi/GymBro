package de.othr.im.gymbro.model;

public record EmailTemplate(String subject, String text) {
    private static final String TEMPLATE = """
              <table style="sans-serif;" border="0" cellpadding="0" cellspacing="0" width="100%%">
                  <tr>
                      <td bgcolor="#f4f4f4" align="center">
                          <!--[if (gte mso 9)|(IE)]>
                          <table align="center" border="0" cellspacing="0" cellpadding="0" width="700">
                              <tr>
                                  <td align="center" valign="top" width="700">
                          <![endif]-->
                          <table border="0" cellpadding="0" cellspacing="8" width="100%%" style="max-width: 700px;">
                              <tr>
                                  <td align="center" valign="top" style="padding: 24px 0 24px 0;">
                                      <h1>Gym Bro</h1>
                                  </td>
                              </tr>
                              <tr bgcolor="white">
                                  <td align="center" valign="top" style="padding: 24px 16px 24px 16px;">
                                      <!-- HEADLINE -->
                                      <h2>%s</h2>
                                      <p style="text-align: center; margin-bottom: 24px">%s</p>
                                      <a style="text-decoration: none; border-radius: 3px; background-color: #0d6efd; border: 12px solid #0d6efd;color: white;" href="%s">%s</a>
                                  </td>
                              </tr>
                              <tr bgcolor="white">
                                  <td align="center" valign="top" style="color: gray; padding: 24px 16px 24px 16px;">
                                      <h2>Gym Bro Tracker</h2>
                                      <p style="padding-bottom: 8px">
                                          <strong>Copyright ©</strong><br/>
                                          OTH-R IM<br/>
                                      </p>
                                  </td>
                              </tr>
                          </table>
                          <!--[if (gte mso 9)|(IE)]>
                          </td>
                          </tr>
                          </table>
                          <![endif]-->
                      </td>
                  </tr>
                </table>
            """;

    public static String sharing(Long planId) {
        return TEMPLATE.formatted(
                "A user shared a workout plan with you",
                "Hi! A user shared a workout plan with you. You can follow the plan by clicking the button below",
                "http://localhost:8080/workout_plans/" + planId + "/follow" //TODO change to production url
                , "Follow Plan"
        );
    }

}
