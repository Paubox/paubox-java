<img src="https://github.com/Paubox/paubox-java/raw/master/paubox_logo.png" alt="Paubox" width="150px">

# Paubox Java

This is the official Java wrapper for the [Paubox Transactional Email API](https://www.paubox.com/solutions/email-api). It is currently in alpha development.

The Paubox Transactional Email API allows your application to send secure, HIPAA-compliant email via Paubox and track deliveries and opens.
The API wrapper allows you to construct and send messages.

# Table of Contents
* [Installation](#installation)
*  [Usage](#usage)
*  [Contributing](#contributing)
*  [License](#license)

<a name="#installation"></a>
## Installation

Add the jar file (Paubox.Email.API.jar) in the classpath of your Java project.

### Getting Paubox API Credentials

You can [sign-up for an account here](https://www.paubox.com/solutions/email-api). Once you have an account, follow the instructions on the Rest API dashboard to verify domain ownership and generate API credentials.

### Configuring API Credentials

Include your API credentials in a configuration file.

Create a configuration properties file, and add following 2 properties to this file:

```java
APIKEY: Your-API-Key-Here
APIUSER: Your-Username-Here
```

Give path to this configuration file as input to ConfigurationManager.getProperties()
method. e.g.
ConfigurationManager.getProperties("E:\\projects\\PauboxTest\\src\\resources\\confi
g.properties");

<a name="#usage"></a>
## Usage

To send email, prepare a Message object and call SendMessage method of
EmailService using EmailInterface: 

### Sending messages

```java
static SendMessageResponse SendMessage()
{
 Message message = new Message();
 Content content = new Content();
 Header header = new Header();
 message.setRecipients(new String[] { "someone@domain.com",
 “someoneelse@domain.com“ });
 header.setFrom("you@yourdomain.com");
 message.setBcc(new String[] { "bcc-recipient@domain.com" });
 header.setSubject("Testing!");
 header.setReplyTo("reply-to@yourdomain.com");
 content.setPlainText("Hello World!");
 message.setHeader(header);
 message.setContent(content);

 EmailInterface email = new EmailService();
 SendMessageResponse response = email.SendMessage(message);
 return response;
}
```

### Allowing non-TLS message delivery

If you want to send non-PHI mail that does not need to be HIPAA-compliant, you can allow the message delivery to take place even if a TLS connection is unavailable.

This means a message will not be converted into a secure portal message when a nonTLS connection is encountered. For this, just pass true to message.setAllowNonTLS() method, as shown below:

```java
static SendMessageResponse SendNonTLSMessage()
{
 Message message = new Message();
 Content content = new Content();
 Header header = new Header();
 message.setRecipients(new String[] { "someone@domain.com",
 “someoneelse@domain.com“ });
 header.setFrom("you@yourdomain.com");
 message.setBcc(new String[] { "bcc-recipient@domain.com" });
 header.setSubject("Testing!");
 header.setReplyTo("reply-to@yourdomain.com");
 content.setPlainText("Hello World!");
 message.setAllowNonTLS(true);
 message.setHeader(header);
 message.setContent(content);

 EmailInterface email = new EmailService();
 SendMessageResponse response = email.SendMessage(message);
 return response;
}
```

### Checking Email Dispositions

To get email status for any source tracking id call GetEmailDisposition method of EmailService using EmailInterface:

```java
static void GetEmailDisposition()
{
 EmailInterface email = new EmailService();
 GetEmailDispositionResponse response =
email.GetEmailDisposition(“2a3c048485aa4cf6”);
}
```

<a name="#contributing"></a>
## Contributing

Bug reports and pull requests are welcome on GitHub at https://github.com/paubox/paubox_ruby.


<a name="#license"></a>
## License

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

## Copyright
Copyright &copy; 2018, Paubox Inc.



