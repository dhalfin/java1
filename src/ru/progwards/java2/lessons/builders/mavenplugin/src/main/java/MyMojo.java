import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.*;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

@Mojo( name = "touch", defaultPhase = LifecyclePhase.PROCESS_SOURCES )
public class MyMojo
        extends AbstractMojo {

    @Parameter(defaultValue = "${project.build.directory}", property = "outputDir", required = true)
    private File outputDirectory;


    @Parameter(defaultValue = "${project.build.finalName}", property = "packageName", required = true)
    private String pkgName;


    @Parameter(defaultValue = "${project.packaging}", property = "packageExt", required = true)
    private String pkgExt;


    @Parameter(defaultValue = "${project.groupId}", property = "groupId", required = true)
    private String groupId;

    @Parameter(defaultValue = "${project.artifactId}", property = "artifactId", required = true)
    private String artifactId;

    @Parameter(defaultValue = "${project.version}", property = "version", required = true)
    private String version;


    @Parameter(property = "emailTo")
    private String emailTo;


    @Parameter(defaultValue = "grigorymail@mail.ru", property = "emailFrom", required = true)
    private String emailFrom;

    @Parameter(defaultValue = "Project ${project.artifactId} compiled", property = "subject", required = true)
    private String subject;

    @Parameter(defaultValue = "smtp.mail.ru", property = "authServ", required = true)
    private String authServ;

    @Parameter(defaultValue = "grigorymail@mail.ru", property = "authUser", required = true)
    private String authUser;

    @Parameter(defaultValue = "********", property = "authPass", required = true)
    private String authPass;

    private final String settingsFileName = "email.settings";

    public void execute() throws MojoExecutionException {
        File f = outputDirectory;
        if (!f.exists())
            f.mkdirs();

        File cfgFile = new File(f, settingsFileName);
        String fileCfg = getFileEmail(cfgFile);
        if (fileCfg != null && !fileCfg.isEmpty())
            emailTo = fileCfg;

        if (emailTo == null || emailTo.isEmpty())
            throw new MojoExecutionException("'email' parameter is not set");

        File pkgFile = new File(f, pkgName + '.' + pkgExt);
        sendMail(emailTo, pkgFile);
    }

    private String getFileEmail(File cfgFile) {
        if (cfgFile.exists() && cfgFile.canRead() && !cfgFile.isDirectory())
            try (
                    FileReader fr = new FileReader(cfgFile);
                    BufferedReader br = new BufferedReader(fr)
            ) {
                return br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
    }

    public void sendMail(String toEmail, File pkgFile) {
        Properties props = new Properties();
        props.put("mail.smtp.host", authServ);
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        Session session = Session.getDefaultInstance(props);
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailFrom));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);
            String result = "Email sent successfully, ";

            String html = "<html><head><style type='text/css'>" +
                    "table,th,td{border:1px solid gray;border-collapse:collapse}" +
                    "th,td{padding:0.3em}" +
                    "</style></head>" +
                    "<body><h1>" + message.getSubject() + "</h1>" +
                    "<table border=1>" +
                    "<tr><td>groupId</td><td>" + groupId + "</td></tr>" +
                    "<tr><td>artifactId</td><td>" + artifactId + "</td></tr>" +
                    "<tr><td>version</td><td>" + version + "</td></tr>" +
                    "</table><p>verified.</p>" +
                    "<p>" + pkgFile.getAbsolutePath() + " attached.</p>" +
                    "<small>This messege was sent by my test mavenplugin</small>" +
                    "</body></html>";

            Multipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(html, "text/html; charset=utf-8");
            multipart.addBodyPart(messageBodyPart);

            if (pkgFile.exists() && pkgFile.canRead() && !pkgFile.isDirectory()) {
                messageBodyPart = new MimeBodyPart();
                messageBodyPart.attachFile(pkgFile);
                multipart.addBodyPart(messageBodyPart);
                result += "package "+pkgName+" attached.";
            } else {
                result += "package "+pkgName+" was not attached - not found.";
            }

            message.setContent(multipart);

            Transport.send(message, authUser, authPass);

            System.out.println(result);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

}
